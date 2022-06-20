import * as React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PageNotFound from "./components/PageNotFound";
import MainPage from "./components/MainPage";
import SignUpForm from "./components/SignUpForm";
import SignInForm from "./components/SignInForm";
import Rental from "./components/Rental";


function App() {
  return (
    <Router>
    <Routes>
        <Route path='/' element={ <MainPage /> } />
        <Route path='/sign-up' element={ <SignUpForm /> } />
        <Route path='/sign-in' element={ <SignInForm /> } />
        <Route path='/rental' element={ <Rental /> } />
        <Route path='*' element={ <PageNotFound /> } />
    </Routes>
    </Router>
  );
}

export default App;
