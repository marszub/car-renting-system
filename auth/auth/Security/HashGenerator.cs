using System.Security.Cryptography;
using System.Text;

namespace auth.Security
{
    public class HashGenerator
    {
        public static string GetHash(string input)
        {
            using (SHA256 sha256Hash = SHA256.Create())
            {
                byte[] data = sha256Hash.ComputeHash(Encoding.UTF8.GetBytes(input));
                return Convert.ToBase64String(data);
            }
        }

        public static bool VerifyHash(string input, string hash)
        {
            var hashOfInput = GetHash(input);

            StringComparer comparer = StringComparer.Ordinal;

            return comparer.Compare(hashOfInput, hash) == 0;
        }
    }
}
