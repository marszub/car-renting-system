using Microsoft.EntityFrameworkCore;
using auth.Models;
using Microsoft.EntityFrameworkCore.Storage;
using Microsoft.EntityFrameworkCore.Infrastructure;
using auth.DataObject;
using Microsoft.EntityFrameworkCore.ChangeTracking;
using auth.Security;

namespace auth.Data
{
    public class AuthContext : DbContext
    {
        public AuthContext(DbContextOptions<AuthContext> options)
            : base(options)
        {
        }

        public DbSet<User> Users { get; set; } = default!;
        public DbSet<Role> Roles { get; set; } = default!;
        public DbSet<Token> Tokens { get; set; } = default!;

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.UseSerialColumns();
            modelBuilder.Entity<Role>().ToTable("Roles");
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
            user.Role = Roles.Single(role => role.Name == "User");
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
