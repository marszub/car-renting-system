using auth.Data;
using auth.DataObject;
using auth.Models;
using Microsoft.AspNetCore.Mvc;
using System.ComponentModel.DataAnnotations;

namespace auth.Controllers
{
    [ApiController]
    [Route("api/auth")]
    public class AuthController : ControllerBase
    {
        private readonly AuthContext context;
        private readonly ILogger<AuthController> _logger;

        public AuthController(AuthContext context, ILogger<AuthController> logger)
        {
            this.context = context;
            _logger = logger;
        }

        [HttpPost("register")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status409Conflict)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public /*async Task<*/IActionResult/*>*/ Register([Required] RegisterData data)
        {
            try
            {
                context.RegisterUser(data);
            }
            catch (UserAlreadyExistsException)
            {
                return Conflict();
            }
            
            return Ok();
        }

        [HttpGet("token")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        public /*async Task<*/IActionResult/*>*/ Login([Required] LoginData data)
        {
            return Unauthorized();
        }

        [HttpPost("logout")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        public /*async Task<*/IActionResult/*>*/ Logout()
        {
            return Ok();
        }
    }
}
