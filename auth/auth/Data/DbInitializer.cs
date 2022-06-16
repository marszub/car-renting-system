using auth.Models;

namespace auth.Data
{
    public static class DbInitializer
    {
        public static void Initialize(AuthContext context)
        {
            if (!context.Roles.Where((role => role.Name == "User")).Any())
            {
                context.Roles.Add(new Role(1, Auth.Role.User.ToString(), "Regular user without additional privilages"));
                context.SaveChanges();
            }
        }
    }
}
