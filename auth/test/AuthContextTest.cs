using auth.Data;
using auth.DataObject;
using test.Utils;

namespace test
{
    public class AuthContextTest : IClassFixture<TestDatabaseFixture>
    {
        private static readonly string login1 = "1login123456";
        private static readonly string email1 = "1user@email.com";
        private static readonly string password1 = "1password12345";
        private static readonly string login2 = "2login123456";
        private static readonly string email2 = "2user@email.com";
        private static readonly string password2 = "2password12345";

        private static readonly RegisterData registerData1 = new RegisterData
        {
            Login = login1,
            Email = email1,
            Password = password1
        };
        private static readonly RegisterData registerData2 = new RegisterData
        {
            Login = login2,
            Email = email2,
            Password = password2
        };

        private static readonly LoginData loginDataLogin1 = new LoginData
        {
            Login = login1,
            Password = password1
        };
        private static readonly LoginData loginDataEmail1 = new LoginData
        {
            Login = email1,
            Password = password1
        };
        private static readonly LoginData loginDataLogin2 = new LoginData
        {
            Login = login2,
            Password = password2
        };
        private static readonly LoginData loginDataEmail2 = new LoginData
        {
            Login = email2,
            Password = password2
        };

        public AuthContextTest(TestDatabaseFixture fixture)
        {
            Fixture = fixture;
            
        }

        public TestDatabaseFixture Fixture { get; }

        [Fact]
        public void DbInitializer()
        {
            using var context = Fixture.CreateContext();

            var querry = context.Roles.Where(role => role.RoleID == 1 && role.Name == "User");

            Assert.True(querry.Any());
            Assert.True(querry.Count() == 1);
        }

        [Fact]
        public void RegisterSingleUser()
        {
            using var context = Fixture.CreateContext();
            context.Database.BeginTransaction();

            context.RegisterUser(registerData1);

            Assert.True(context.Users.Any());
            var createdUsers = context.Users.Where(
                user => user.Login == login1 &&
                user.Email == email1);
            Assert.True(createdUsers.Any());

            context.ChangeTracker.Clear();
        }

        [Fact]
        public void RegisteredUserHasEncryptedPassword()
        {
            using var context = Fixture.CreateContext();
            context.Database.BeginTransaction();

            context.RegisterUser(registerData1);

            Assert.True(context.Users.Any());
            var createdUsers = context.Users.Where(user => user.Password != password1 && user.Password.Length > 0);
            Assert.True(createdUsers.Any());

            context.ChangeTracker.Clear();
        }

        [Fact]
        public void RegisteredUsersGetUserRole()
        {
            using var context = Fixture.CreateContext();
            context.Database.BeginTransaction();

            context.RegisterUser(registerData1);
            context.RegisterUser(registerData2);

            Assert.True(context.Users.All(
                user => user.Role == context.Roles.Single(role => role.Name == "User")));

            context.ChangeTracker.Clear();
        }

        [Fact]
        public void CanNotRegisterUsersWithSameLoginOrEmail()
        {
            using var context = Fixture.CreateContext();
            context.Database.BeginTransaction();

            context.RegisterUser(registerData1);
            Assert.Throws<UserAlreadyExistsException>(() => context.RegisterUser(
                new RegisterData { Login = login1, Email = email2, Password = password2 }));
            Assert.Throws<UserAlreadyExistsException>(() => context.RegisterUser(
                new RegisterData { Login = login2, Email = email1, Password = password2 }));

            Assert.Equal(1, context.Users.Where(user => user.Login == login1).Count());

            context.ChangeTracker.Clear();
        }

        [Fact]
        public void RegisteredUsersHaveDifferentIds()
        {
            using var context = Fixture.CreateContext();
            context.Database.BeginTransaction();

            context.RegisterUser(registerData1);
            context.RegisterUser(registerData2);

            Assert.Equal(2, context.Users.Select(user => user.UserID).Distinct().Count());

            context.ChangeTracker.Clear();
        }

        private static IEnumerable<object[]> RegisterAndLoginUsers()
        {
            return new List<object[]>
            {
                new object[] { registerData1, loginDataLogin1 },
                new object[] { registerData1, loginDataEmail1 },
                new object[] { registerData2, loginDataLogin2 },
                new object[] { registerData2, loginDataEmail2 },
            };
        }

        [Theory]
        [MemberData(nameof(RegisterAndLoginUsers))]
        public void LogingSingleUser(RegisterData registerData, LoginData loginData)
        {
            using var context = Fixture.CreateContext();
            context.Database.BeginTransaction();

            context.RegisterUser(registerData);
            string generatedToken = context.CreateToken(loginData);

            Assert.True(context.Tokens
                .Where(token => token.Owner.Login == registerData.Login
                && token.Owner.Email == registerData.Email
                && token.Value == generatedToken)
                .Any());

            context.ChangeTracker.Clear();
        }

        [Theory]
        [MemberData(nameof(RegisterAndLoginUsers))]
        public void TokenForSameUserChange(RegisterData registerData, LoginData loginData)
        {
            using var context = Fixture.CreateContext();
            context.Database.BeginTransaction();

            context.RegisterUser(registerData);
            string token1 = context.CreateToken(loginData);
            string token2 = context.CreateToken(loginData);

            Assert.NotEqual(token1, token2);

            context.ChangeTracker.Clear();
        }

        [Theory]
        [MemberData(nameof(RegisterAndLoginUsers))]
        public void BothTokensForSameUserAreSaved(RegisterData registerData, LoginData loginData)
        {
            using var context = Fixture.CreateContext();
            context.Database.BeginTransaction();

            context.RegisterUser(registerData);
            string token1 = context.CreateToken(loginData);
            string token2 = context.CreateToken(loginData);

            Assert.Equal(2, context.Tokens.Count());

            context.ChangeTracker.Clear();
        }

        [Fact]
        public void NotCreateTokenForNotExistingUser()
        {
            using var context = Fixture.CreateContext();
            context.Database.BeginTransaction();

            context.RegisterUser(registerData1);
            Assert.Throws<BadUserCredentialsException>(() => context.CreateToken(loginDataLogin2));

            context.ChangeTracker.Clear();
        }

        [Fact]
        public void NotCreateTokenForWrongPassword()
        {
            using var context = Fixture.CreateContext();
            context.Database.BeginTransaction();

            context.RegisterUser(registerData1);
            Assert.Throws<BadUserCredentialsException>(() => context.CreateToken(new LoginData { Login = login1, Password = password2 }));
            Assert.False(context.Tokens.Any());

            context.ChangeTracker.Clear();
        }
    }
}
