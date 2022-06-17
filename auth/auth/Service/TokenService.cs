using auth.Data;
using auth.DataObject;
using auth.Models;
using auth.Security;
using Microsoft.EntityFrameworkCore;

namespace auth.Service
{
    public class TokenService
    {
        private AuthContext context;

        public TokenService(AuthContext context)
        {
            this.context = context;
        }

        public string CreateToken(LoginData data)
        {
            User? user = GetVerifiedUser(data.Login, data.Password);
            if (user == null)
            {
                throw new BadUserCredentialsException();
            }

            string tokenValue = HashGenerator.GetHash(user.Login) + HashGenerator.GetHash(user.Email) + HashGenerator.GetHash(DateTime.Now.ToString("yyyy.MM.dd HH:mm:ss:ffff"));

            user.Tokens.Add(new Token(tokenValue));
            context.SaveChanges();

            return tokenValue;
        }

        public void DeleteToken(string token)
        {
            var found = context.Tokens.Where(entity => entity.Value == token);
            if (!found.Any())
            {
                throw new UnauthorizedAccessException();
            }
            context.Tokens.RemoveRange(found.ToList());
            context.SaveChanges();
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
