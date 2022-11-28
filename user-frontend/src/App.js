import * as React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PageNotFound from "./components/PageNotFound";
import MainPage from "./components/MainPage";
import Tarrif from "./components/Tarrif";
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
import AdminError from "./components/Admin/AdminError";
import AdminPayments from "./components/Admin/AdminPayments";

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
          <Route path='/rental' element={ <Rental /> } />
          <Route path='/tarrif' element={ <Tarrif/> }/>
          <Route path='/admin/sign-in' element={ <AdminSignInForm /> } />
          <Route path="/admin/cars" element={ <AdminCars/>} />
          <Route path="/admin/cars/new" element={ <AddCar/>} />
          <Route path="/admin/carCategories/new" element={ <AddCarCategory/>} />
          <Route path="/admin/carCategories" element={ <AdminCarCategories/>} />
          <Route path="/admin/error" element={ <AdminError/>} />
          <Route path="/admin/payments" element={ <AdminPayments/>} />
          <Route path='*' element={ <PageNotFound /> } />
      </Routes>
    </Router>
  );
}

export default App;
