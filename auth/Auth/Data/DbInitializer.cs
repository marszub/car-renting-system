namespace Auth.Data
{
    public static class DbInitializer
    {
        public static void Initialize(AuthContext context)
        {
            if (!context.Roles.Where(role => role.Name == Role.User.ToString() || role.RoleID == 1).Any())
            {
                context.Roles.Add(new Models.Role(1, Role.User.ToString(), "Regular user without additional privilages"));
            }
            if (!context.Roles.Where(role => role.Name == Role.Admin.ToString() || role.RoleID == 2).Any())
            {
                context.Roles.Add(new Models.Role(2, Role.Admin.ToString(), "Admin with full access to application"));
                context.TryAddUniqueUser("root", "", "password", Role.Admin);
            }
            context.SaveChanges();
        }
    }
}
