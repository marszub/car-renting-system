using auth.Data;
using auth.DataObject;
using auth.Service;
using test.Utils;

namespace test
{
    public class TokenServiceTest : IClassFixture<TestDatabaseFixture>
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

        private AuthContext context { get; }
        private TokenService tokenService { get; }

        public TokenServiceTest(TestDatabaseFixture fixture)
        {
            context = fixture.CreateContext();
            context.Database.BeginTransaction();
            tokenService = new TokenService(context);
        }

        ~TokenServiceTest()
        {
            context.ChangeTracker.Clear();
        }

        [Theory]
        [MemberData(nameof(RegisterAndLoginUsers))]
        public void LogingSingleUser(RegisterData registerData, LoginData loginData)
        {
            context.RegisterUser(registerData);
            string generatedToken = tokenService.CreateToken(loginData);

            Assert.True(context.Tokens
                .Where(token => token.Owner.Login == registerData.Login
                && token.Owner.Email == registerData.Email
                && token.Value == generatedToken)
                .Any());
        }

        [Theory]
        [MemberData(nameof(RegisterAndLoginUsers))]
        public void TokenForSameUserChange(RegisterData registerData, LoginData loginData)
        {
            context.RegisterUser(registerData);
            string token1 = tokenService.CreateToken(loginData);
            string token2 = tokenService.CreateToken(loginData);

            Assert.NotEqual(token1, token2);
        }

        [Theory]
        [MemberData(nameof(RegisterAndLoginUsers))]
        public void BothTokensForSameUserAreSaved(RegisterData registerData, LoginData loginData)
        {
            context.RegisterUser(registerData);
            string token1 = tokenService.CreateToken(loginData);
            string token2 = tokenService.CreateToken(loginData);

            Assert.Equal(2, context.Tokens.Count());
        }

        [Fact]
        public void NotCreateTokenForNotExistingUser()
        {
            context.RegisterUser(registerData1);
            Assert.Throws<BadUserCredentialsException>(() => tokenService.CreateToken(loginDataLogin2));
        }

        [Fact]
        public void NotCreateTokenForWrongPassword()
        {
            context.RegisterUser(registerData1);
            Assert.Throws<BadUserCredentialsException>(() => tokenService.CreateToken(new LoginData { Login = login1, Password = password2 }));
            Assert.False(context.Tokens.Any());
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
        public void DeleteExistingToken(RegisterData registerData, LoginData loginData)
        {
            context.RegisterUser(registerData);
            string token = tokenService.CreateToken(loginData);
            tokenService.DeleteToken(token);
            Assert.False(context.Tokens.Any());
        }

        [Theory]
        [MemberData(nameof(RegisterAndLoginUsers))]
        public void DeleteOnlyOneToken(RegisterData registerData, LoginData loginData)
        {
            context.RegisterUser(registerData);
            tokenService.CreateToken(loginData);
            string token = tokenService.CreateToken(loginData);
            tokenService.DeleteToken(token);
            Assert.True(context.Tokens.Any());
        }

        [Theory]
        [MemberData(nameof(RegisterAndLoginUsers))]
        public void ThrowWhenDeleteWrongToken(RegisterData registerData, LoginData loginData)
        {
            context.RegisterUser(registerData);
            tokenService.CreateToken(loginData);
            Assert.Throws<UnauthorizedAccessException>(() => tokenService.DeleteToken("wrong token"));
            Assert.True(context.Tokens.Any());
        }
    }
}
