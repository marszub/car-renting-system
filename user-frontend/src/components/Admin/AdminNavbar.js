import { AppBar, Toolbar, Typography, Box } from "@mui/material";
import * as React from "react";
import { useNavigate, Link } from "react-router-dom";
import logo from "../../assets/logo.jpg"
import { tokenAdminStorage } from "../../services/token-admin-storage";
import { AccountCircleOutlined } from "@mui/icons-material"
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';
import { AdminAuthService } from "../../services/admin-auth-service";
import { HTTP_BAD_REQUEST, HTTP_NO_CONTENT } from "../../utils/http-status";

export default function AdminNavbar() {
    const navigate = useNavigate();
    const [, updateState] = React.useState();
    const [anchorEl, setAnchorEl] = React.useState(null);

    const handleMenu = (event) => {
      setAnchorEl(event.currentTarget);
    };
  
    const handleClose = () => {
      setAnchorEl(null);
      logOut();
    };

    const logOut = () => {
        const service = new AdminAuthService();
        service.signOut().then(res => {
            switch (res.status) {
                case HTTP_NO_CONTENT:
                    console.log("Logged out");
                    navigate("/admin/sign-in");
                    break;
                case HTTP_BAD_REQUEST:
                    console.log("Bad request");
                    navigate("/error");
                    break;
                default:
                    console.log("Internal server error");
                    navigate("/error");
                    break;
            }
        }).catch(err => {
            console.log("err");
            navigate("/error");
        });
    }

    return(
    <AppBar position="fixed">
      <Toolbar sx={{
          backgroundColor: "#2E86AB"
      }}>
          <Box 
            component="img"
            sx={{
              userSelect: 'none',
              height: '50px'
            }}
            src={logo}
          >
          </Box>
          <Typography
            variant="h6"
            noWrap
            sx={{
              paddingLeft: "10px",
              mr:2,
              textDecoration: 'none',
              color: 'inherit',
              userSelect: 'none'
            }}
          >
              <Link to="/admin" style={{textDecoration: 'none', color: 'inherit'}}>CarSharingAGH</Link>
          </Typography>
          {
            tokenAdminStorage.accessToken ?
            (
              <div style = {{display: "flex", flexGrow: 1}}>
                <Typography
                  variant="h6"
                  noWrap
                  sx={{
                    mr:2,
                    textDecoration: 'none',
                    color: 'inherit',
                    userSelect: 'none'
                  }}
                >
                    <Link to="/admin/cars" style={{textDecoration: 'none', color: 'inherit'}}>Cars</Link>
                </Typography>
                <Typography
                  variant="h6"
                  noWrap
                  sx={{
                    mr:2,
                    textDecoration: 'none',
                    color: 'inherit',
                    userSelect: 'none'
                  }}
                >
                    <Link to="/admin/carCategories" style={{textDecoration: 'none', color: 'inherit'}}>CarCategories</Link>
                </Typography>
              </div>
            ) : <div></div>
          }
          {tokenAdminStorage.accessToken ?
              (
                <Box>
                  <AccountCircleOutlined style={{marginRight: "10px", fontSize: "35px", display: "flex"}} onClick={handleMenu}/>
                  <Menu
                    id="menu-appbar"
                    anchorEl={anchorEl}
                    anchorOrigin={{
                      vertical: 'top',
                      horizontal: 'right',
                    }}
                    keepMounted
                    transformOrigin={{
                      vertical: 'top',
                      horizontal: 'right',
                    }}
                    open={Boolean(anchorEl)}
                    onClose={handleClose}
                  >
                    <MenuItem onClick={handleClose}>Log out</MenuItem>
                  </Menu>
                </Box>
              ) : <div></div>
          }
      </Toolbar>
    </AppBar>
    )
}
