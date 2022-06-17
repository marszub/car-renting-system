namespace Auth.Data
{
    public static class DbInitializer
    {
        public static void Initialize(AuthContext context)
        {
            if (!context.Roles.Where((role => role.Name == "User")).Any())
            {
                context.Roles.Add(new Models.Role(1, Role.User.ToString(), "Regular user without additional privilages"));
                context.SaveChanges();
            }
        }
    }
}
