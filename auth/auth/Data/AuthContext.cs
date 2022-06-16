using Microsoft.EntityFrameworkCore;
using Auth.Models;
using Auth.DataObject;
using Auth.Security;

namespace Auth.Data
{
    public class AuthContext : DbContext
    {
        public AuthContext(DbContextOptions<AuthContext> options)
            : base(options)
        {
        }

        public DbSet<User> Users { get; set; } = default!;
        public DbSet<Models.Role> Roles { get; set; } = default!;
        public DbSet<Token> Tokens { get; set; } = default!;

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.UseSerialColumns();
            modelBuilder.Entity<Models.Role>().ToTable("Roles");
            modelBuilder.Entity<User>().ToTable("Users");
            modelBuilder.Entity<Token>().ToTable("Tokens");

            modelBuilder.Entity<User>().HasOne(user => user.Role)
                .WithMany(role => role.Users)
                .HasForeignKey(user => user.RoleID)
                .IsRequired();
            modelBuilder.Entity<Token>().HasOne(token => token.Owner)
                .WithMany(user => user.Tokens)
                .HasForeignKey(token => token.UserID)
                .IsRequired();
        }

        public void RegisterUser(RegisterData data)
        {
            if(UserExists(data.Login, data.Email))
            {
                throw new UserAlreadyExistsException();
            }

            User user = new User(data.Login, data.Email, HashGenerator.GetHash(data.Password));
            user.Role = Roles.Single(role => role.Name == Auth.Role.User.ToString());
            Users.Add(user);

            SaveChanges();
        }

        private bool UserExists(string Login, string Email)
        {
            return Users.Where((User user)
                => user.Login == Login
                || user.Email == Email).Any();
        }
    }
}
