namespace auth.Models
{
    public class Token
    {
        public int TokenID { get; set; }
        public string Value { get; set; }
        public int UserID { get; set; }
        public User Owner { get; set; } = default!;

        public Token(string value)
        {
            Value = value;
        }

        public Token(string value, User user)
        {
            Owner = user;
            Value = value;
        }
    }
}
