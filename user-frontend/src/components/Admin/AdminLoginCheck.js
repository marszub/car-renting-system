import { tokenAdminStorage } from "../../services/token-admin-storage";
import { useNavigate } from "react-router-dom";
import React, { useEffect } from 'react';

export default function AdminLoginCheck() {
    const navigate = useNavigate();
    
    useEffect(() => {
        if(!tokenAdminStorage.accessToken && !window.location.href.includes("/admin/sign-in")) {
            navigate("/admin/sign-in");
        }
      }, []);

    if(!tokenAdminStorage.accessToken && !window.location.href.includes("/admin/sign-in")) {
        navigate("/admin/sign-in");
    }
}

