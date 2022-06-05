using Microsoft.EntityFrameworkCore;
using auth.Models;

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
        public DbSet<RolePossesion> RolePossesions { get; set; }


        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<User>().ToTable("User");
            modelBuilder.Entity<Role>().ToTable("Role");
            modelBuilder.Entity<RolePossesion>().ToTable("RolePossesion");
        }

        /*public User? RegisterUser(RegisterData data)
        {
            if(Users.Where((User user) => user.Login == data.Login || user.Email == data.Email).Any())
            {
                return null;
            }

            User user = new User(data.Login, data.Email, data.Password);
            Users.Add(user);
            Users.

            RolePossesion possesion = new RolePossesion(entry.)

            return user;
        }*/
    }
}
