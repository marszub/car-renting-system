namespace auth.Models
{
    using System.ComponentModel.DataAnnotations.Schema;

    public class Token
    {
        public int TokenID { get; set; }
        public User User { get; set; }
        public string Value { get; set; }

        public Token(User user, string value)
        {
            User = user;
            Value = value;
        }
    }
}
