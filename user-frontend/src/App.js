import * as React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PageNotFound from "./components/PageNotFound";
import MainPage from "./components/MainPage";
import SignUpForm from "./components/SignUpForm";
import SignInForm from "./components/SignInForm";
import AdminCars from "./components/Admin/AdminCars";
import AddCar from "./components/Admin/AddCar";
import Rental from "./components/Rental";
import Navbar from "./components/Navbar"
import AddCarCategory from "./components/Admin/AddCarCategory"
import AdminCarCategories from "./components/Admin/AdminCarCategories"
import AdminNavbar from "./components/Admin/AdminNavbar";
import AdminSignInForm from "./components/Admin/AdminSignInForm";
import AdminLoginCheck from "./components/Admin/AdminLoginCheck";

function App() {
  return (
    <Router>
      <Routes>
          <Route path='/admin' element={ <AdminLoginCheck/> } />
          <Route path='/admin/*' element={ <AdminLoginCheck/> } />
          <Route path="*" element={null}/>
      </Routes>
      <Routes>
          <Route path='/admin/*' element={ <AdminNavbar /> } />
          <Route path='*' element={ <Navbar /> } />
      </Routes>
      <Routes>
          <Route path='/' element={ <MainPage /> } />
          <Route path='/admin' element={ <MainPage /> } />
          <Route path='/sign-up' element={ <SignUpForm /> } />
          <Route path='/sign-in' element={ <SignInForm /> } />
          <Route path='/admin/sign-in' element={ <AdminSignInForm /> } />
          <Route path='/rental' element={ <Rental /> } />
          <Route path="admin/cars" element={ <AdminCars/>} />
          <Route path="admin/cars/new" element={ <AddCar/>} />
          <Route path="admin/carCategories/new" element={ <AddCarCategory/>} />
          <Route path="admin/carCategories" element={ <AdminCarCategories/>} />
          <Route path='*' element={ <PageNotFound /> } />
      </Routes>
    </Router>
  );
}

export default App;
