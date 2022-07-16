using Auth.Data;
using Auth.DataObject;
using Auth.Models;
using Auth.Service;
using Microsoft.AspNetCore.Mvc;
using System.ComponentModel.DataAnnotations;

namespace Auth.Controller
{
    [ApiController]
    [Route("api/Auth")]
    public class AuthController : ControllerBase
    {
        private readonly AuthContext context;
        private readonly TokenService tokenService;
        private readonly ILogger<AuthController> _logger;

        public AuthController(AuthContext context, ILogger<AuthController> logger)
        {
            this.context = context;
            this.tokenService = new TokenService(context);
            _logger = logger;
        }

        [HttpPost("register")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status409Conflict)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public ActionResult<AccessToken> Register([Required] RegisterData data)
        {
            try
            {
                context.RegisterUser(data);
            }
            catch (UserAlreadyExistsException)
            {
                return Conflict();
            }

            try
            {
                return Ok(tokenService.CreateToken(new LoginData { Login = data.Login, Password = data.Password }) );
            }
            catch (BadUserCredentialsException)
            {
                return Unauthorized();
            }
        }

        [HttpPost("token")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        public ActionResult<AccessToken> Login([Required] LoginData data)
        {
            try
            {
                return Ok(tokenService.CreateToken(data));
            }
            catch(BadUserCredentialsException)
            {
                return Unauthorized();
            }
        }

        [HttpPost("logout")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        public IActionResult Logout([FromHeader(Name = "userId")] int UserId, [FromHeader(Name = "token")] string Token)
        {
            try
            {
                tokenService.DeleteToken(new AccessToken(UserId, Token));
                return NoContent();
            }
            catch(UnauthorizedAccessException)
            {
                return Unauthorized();
            }
        }

        [HttpPost("user")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        public ActionResult<UserData> UserData([FromHeader(Name = "token")] string Token)
        {
            try
            {
                return Ok(tokenService.GetUser(Token));
            }
            catch (InvalidOperationException)
            {
                return Unauthorized();
            }
        }
    }
}
