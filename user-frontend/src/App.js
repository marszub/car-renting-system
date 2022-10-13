import * as React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PageNotFound from "./components/PageNotFound";
import MainPage from "./components/MainPage";
import SignUpForm from "./components/SignUpForm";
import SignInForm from "./components/SignInForm";
import AdminCars from "./components/Admin/AdminCars";
import AddCar from "./components/Admin/AddCar";
import { Rental } from "./components/Rental";
import Navbar from "./components/Navbar"
import AddCarCategory from "./components/Admin/AddCarCategory"
import AdminCarCategories from "./components/Admin/AdminCarCategories"

function App() {
  return (
    <Router>
      <Navbar/>
      <Routes>
          <Route path='/' element={ <MainPage /> } />
          <Route path='/sign-up' element={ <SignUpForm /> } />
          <Route path='/sign-in' element={ <SignInForm /> } />
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
