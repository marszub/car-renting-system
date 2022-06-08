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
                .IsRequired();
            modelBuilder.Entity<User>().HasMany(user => user.Tokens)
                .WithOne(token => token.Owner)
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

        public string CreateToken(LoginData data)
        {
            User? user = GetVerifiedUser(data.Login, data.Password);
            if (user == null)
            {
                throw new BadUserCredentialsException();
            }

            string tokenValue = HashGenerator.GetHash(user.Login) + HashGenerator.GetHash(user.Email) + HashGenerator.GetHash(DateTime.Now.ToString());

            Token token = new Token(tokenValue, user);
            Tokens.Add(token);
            SaveChanges();

            return tokenValue;
        }

        private bool UserExists(string Login, string Email)
        {
            return Users.Where((User user)
                => user.Login == Login
                || user.Email == Email).Any();
        }

        private User? GetVerifiedUser(string UserId, string Password)
        {
            string hashedPassword = HashGenerator.GetHash(Password);

            return Users.Where((User user)
                => (user.Login == UserId
                || user.Email == UserId)
                && user.Password == hashedPassword)
                .SingleOrDefault();
        }
    }
}
