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

        public bool TryAddUniqueUser(string login, string email, string password, Auth.Role role)
        {
            if (UserExists(login, email))
            {
                return false;
            }

            User user = new User(login, email, HashGenerator.GetHash(password));
            user.Role = Roles.Single(r => r.Name == role.ToString());
            Users.Add(user);
            SaveChanges();

            return true;
        }

        public void RegisterUser(RegisterData data)
        {
            if(TryAddUniqueUser(data.Login, data.Email, data.Password, Auth.Role.User))
            {
                return;
            }

            throw new UserAlreadyExistsException();
        }

        private bool UserExists(string Login, string Email)
        {
            return Users.Where((User user)
                => user.Login == Login
                || user.Email == Email).Any();
        }
    }
}
