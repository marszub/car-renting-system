using Auth.Data;
using Auth.Models;
using Auth;
using Ice;
using Microsoft.EntityFrameworkCore;

namespace Auth.SharedObject
{
    public class AccountDefault : AccountDisp_
    {
        private AuthContext context { get; }
        public AccountDefault(AuthContext context)
        {
            this.context = context;
        }

        public override TokenVerificationStatus verifyToken(AccessData accessData, Current current)
        {
            return verifyTokenImpl(accessData, current.id.name);
        }

        public TokenVerificationStatus verifyTokenImpl(AccessData accessData, string userId)
        {
            User user = GetUser(userId);
            if (!VerifyRole(user, accessData.role))
            {
                return TokenVerificationStatus.RoleNotAssigned;
            }
            if (!VerifyToken(user, accessData.token))
            {
                return TokenVerificationStatus.InvalidToken;
            }
            return TokenVerificationStatus.Ok;
        }

        public override void ice_ping(Ice.Current current)
        {
            GetUser(current.id.name);
        }

        private User GetUser(string id)
        {
            if (!Int32.TryParse(id, out int userId))
            {
                throw new ObjectNotExistException();
            }
            User? user = context.Users.Include(user => user.Tokens).Include(user => user.Role).SingleOrDefault(user => user.UserID == userId);

            if (user == null)
            {
                throw new ObjectNotExistException();
            }
            return user;
        }

        private bool VerifyRole(User user, Auth.Role role)
        {
            return user.Role.Name == role.ToString();
        }

        private bool VerifyToken(User user, string token)
        {
            return user.Tokens.Where(t => t.Value == token).Any();
        }
    }
}
