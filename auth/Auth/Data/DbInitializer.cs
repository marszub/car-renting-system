namespace Auth.Data
{
    public class DbInitializer
    {
        public DbInitializer(AuthContext context)
        {
            this.context = context;
        }

        public DbInitializer InitializeRoles()
        {
            if (!context.Roles.Where(role => role.Name == Role.User.ToString() || role.RoleID == 1).Any())
            {
                context.Roles.Add(new Models.Role(1, Role.User.ToString(), "Regular user without additional privilages"));
                context.SaveChanges();
            }
            if (!context.Roles.Where(role => role.Name == Role.Admin.ToString() || role.RoleID == 2).Any())
            {
                context.Roles.Add(new Models.Role(2, Role.Admin.ToString(), "Admin with full access to application"));
                context.SaveChanges();
            }
            return this;
        }

        public DbInitializer CreateRootUser()
        {
            context.TryAddUniqueUser("root", "", "password", Role.Admin);
            return this;
        }

        private AuthContext context;
    }
}
