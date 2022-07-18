using Auth.Data;
using Ice;

namespace Auth.SharedObject
{
    public class AccountLocator : Ice.ServantLocator
    {
        private readonly IServiceProvider Services;
        public AccountLocator(IServiceProvider services)
        {
            Services = services;
        }
        public void deactivate(string category)
        {
            return;
        }

        public void finished(Current curr, Ice.Object servant, object cookie)
        {
            return;
        }

        public Ice.Object locate(Current curr, out object cookie)
        {
            Console.WriteLine("Locating: " + curr.id.category + " " + curr.id.name);
            cookie = new object();
            return new AccountServant(Services.GetRequiredService<AuthContext>());
        }
    }
}
