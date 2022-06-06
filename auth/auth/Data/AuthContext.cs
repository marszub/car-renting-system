using Microsoft.EntityFrameworkCore;
using auth.Models;
using Microsoft.EntityFrameworkCore.Storage;
using Microsoft.EntityFrameworkCore.Infrastructure;
using auth.DataObject;
using Microsoft.EntityFrameworkCore.ChangeTracking;

namespace auth.Data
{
    public class AuthContext : DbContext
    {
        public AuthContext(DbContextOptions<AuthContext> options)
            : base(options)
        {
        }

        public DbSet<User> Users { get; set; }
        public DbSet<Role> Roles { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.UseSerialColumns();
            modelBuilder.Entity<Role>().ToTable("Roles");
            modelBuilder.Entity<User>().ToTable("Users");
            modelBuilder.Entity<User>().HasOne(user => user.Role)
                .WithMany(role => role.Users)
                .IsRequired();
        }

        public void RegisterUser(RegisterData data)
        {
            if(UserExists(data.Login, data.Email))
            {
                throw new UserAlreadyExistsException();
            }

            Role userRole = Roles.Single(role => role.Name == "User");
            User user = new User(data.Login, data.Email, data.Password, userRole);
            Users.Add(user);

            SaveChanges();
        }

/*        public string GetToken(LoginData data)
        {
            User? user = GetVerifiedUser(data.Login, data.Password);
            if (user == null)
            {
                throw new BadUserCredentialsException();
            }
        }*/

        private bool UserExists(string Login, string Email)
        {
            return Users.Where((User user)
                => user.Login == Login
                || user.Email == Email).Any();
        }

        /*private User? GetVerifiedUser(string UserId, string Password)
        {
            return Users.Where((User user)
                => (user.Login == UserId
                || user.Email == UserId) 
                && user.Password == Password).SingleOrDefault();
        }*/
    }
}
