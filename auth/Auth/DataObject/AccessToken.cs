using Microsoft.AspNetCore.Mvc;
using System.ComponentModel.DataAnnotations;

namespace Auth.DataObject
{
    public class AccessToken
    {
        [Required]
        public int UserID { get; set; }
        [Required]
        public string Token { get; set; }

        public AccessToken(int userID, string token)
        {
            UserID = userID;
            Token = token;
        }
    }
}
