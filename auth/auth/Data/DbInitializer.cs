namespace auth.Data
{
    public static class DbInitializer
    {
        public static void Initialize(AuthContext context)
        {
            if (!context.Roles.Where(((Models.Role role) => role.Name == "User")).Any())
            {
                context.Roles.Add(new Models.Role(1, "User", "Regular user without additional privilages"));
                context.SaveChanges();
            }
        }
    }
}
