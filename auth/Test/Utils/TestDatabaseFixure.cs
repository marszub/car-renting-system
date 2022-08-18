using Auth.Data;
using Auth.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace test.Utils
{
    public class TestDatabaseFixture
    {
        private const string ConnectionString = @"Server=localhost;Port=5011;Database=authTest;Username=postgres;Password=password";

        private static readonly object _lock = new();
        private static bool _databaseInitialized;

        public TestDatabaseFixture()
        {
            if (_databaseInitialized)
            {
                return;
            }
            lock (_lock)
            {
                if (!_databaseInitialized)
                {
                    using (var context = CreateContext())
                    {
                        context.Database.EnsureDeleted();
                        context.Database.EnsureCreated();

                        new DbInitializer(context).InitializeRoles();
                    }

                    _databaseInitialized = true;
                }
            }
        }

        public AuthContext CreateContext()
            => new AuthContext(
                new DbContextOptionsBuilder<AuthContext>()
                    .UseNpgsql(ConnectionString)
                    .Options);
    }
}
