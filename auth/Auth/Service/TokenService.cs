using Auth.Data;
using Auth.DataObject;
using Auth.Models;
using Auth.Security;
using Microsoft.EntityFrameworkCore;

namespace Auth.Service
{
    public class TokenService
    {
        private AuthContext context;

        public TokenService(AuthContext context)
        {
            this.context = context;
        }

        public AccessToken CreateToken(LoginData data)
        {
            User? user = GetVerifiedUser(data.Login, data.Password);
            if (user == null)
            {
                throw new BadUserCredentialsException();
            }

            string tokenValue = HashGenerator.GetHash(user.Login) + HashGenerator.GetHash(user.Email) + HashGenerator.GetHash(DateTime.Now.ToString("yyyy.MM.dd HH:mm:ss:ffff"));

            user.Tokens.Add(new Token(tokenValue));
            context.SaveChanges();

            return new AccessToken(user.UserID, tokenValue);
        }

        public void DeleteToken(AccessToken token)
        {
            var found = context.Tokens.Where(entity => entity.Value == token.Token && entity.UserID == token.UserID);
            if (!found.Any())
            {
                throw new UnauthorizedAccessException();
            }
            context.Tokens.RemoveRange(found.ToList());
            context.SaveChanges();
        }

        public UserData GetUser(string token)
        {
            User user = context.Tokens
                .Include(token => token.Owner)
                .ThenInclude((User user) => user.Role)
                .Single(t => t.Value == token)
                .Owner;

            return new UserData(user);
        }

        private User? GetVerifiedUser(string UserId, string Password)
        {
            string hashedPassword = HashGenerator.GetHash(Password);

            return context.Users.Include(user => user.Tokens).Where((User user)
                => (user.Login == UserId
                || user.Email == UserId)
                && user.Password == hashedPassword)
                .SingleOrDefault();
        }
    }
}
