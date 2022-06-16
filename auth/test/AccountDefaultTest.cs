using auth.Data;
using auth.DataObject;
using auth.Models;
using auth.Service;
using auth.SharedObject;
using test.Utils;
using Auth;
using Microsoft.EntityFrameworkCore;
using Ice;

namespace test
{
    public class AccountDefaultTest : IClassFixture<TestDatabaseFixture>
    {
        private static readonly string login1 = "1login123456";
        private static readonly string email1 = "1user@email.com";
        private static readonly string password1 = "1password12345";
        private static readonly string login2 = "2login123456";
        private static readonly string email2 = "2user@email.com";
        private static readonly string password2 = "2password12345";

        private readonly int userId1;
        private readonly int userId2;

        private static readonly string token1 = "token1";
        private static readonly string token2 = "token2";
        private static readonly string token3 = "token3";

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
        private AccountDefault servant { get; }

        public AccountDefaultTest(TestDatabaseFixture fixture)
        {
            context = fixture.CreateContext();
            servant = new AccountDefault(context);

            context.Database.BeginTransaction();

            context.RegisterUser(registerData1);
            context.RegisterUser(registerData2);

            context.SaveChanges();

            userId1 = context.Users.Single(user => user.Login == login1).UserID;
            userId2 = context.Users.Single(user => user.Login == login2).UserID;
        }

        ~AccountDefaultTest()
        {
            context.ChangeTracker.Clear();
        }

        [Fact]
        public void VerifyValidToken()
        {
            User user = context.Users.Include(user => user.Tokens).Single(user => user.Login == login1);
            user.Tokens.Add(new Token(token1));
            context.SaveChanges();

            TokenVerificationStatus returnedStatus = servant.verifyTokenImpl(
                new AccessData(token1, Auth.Role.User), userId1.ToString());

            Assert.Equal(TokenVerificationStatus.Ok, returnedStatus);
        }

        [Fact]
        public void VerifyInvalidToken()
        {
            User user = context.Users.Include(user => user.Tokens).Single(user => user.Login == login1);
            user.Tokens.Add(new Token(token1));
            context.SaveChanges();

            TokenVerificationStatus returnedStatus = servant.verifyTokenImpl(
                new AccessData(token2, Auth.Role.User), userId1.ToString());

            Assert.Equal(TokenVerificationStatus.InvalidToken, returnedStatus);
        }

        [Fact]
        public void VerifyInvalidToken_ValidForDifferentUser()
        {
            User user1 = context.Users.Include(user => user.Tokens).Single(user => user.Login == login1);
            user1.Tokens.Add(new Token(token1));
            context.SaveChanges();

            TokenVerificationStatus returnedStatus = servant.verifyTokenImpl(
                new AccessData(token1, Auth.Role.User), userId2.ToString());

            Assert.Equal(TokenVerificationStatus.InvalidToken, returnedStatus);
        }

        [Fact]
        public void VerifyTokenNotExistingUser()
        {
            Assert.Throws<ObjectNotExistException>(() => servant.verifyTokenImpl(
                new AccessData(token1, Auth.Role.User), (Math.Max(userId1, userId2) + 1).ToString()));
        }
    }
}
