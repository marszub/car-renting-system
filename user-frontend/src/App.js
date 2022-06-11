import * as React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import PageNotFound from "./components/PageNotFound";
import MainPage from "./components/MainPage";
import SignUpForm from "./components/SignUpForm";


function App() {
  return (
    <Router>
    <Routes>
        <Route path='/' element={ <MainPage /> } />
        <Route path='/sign-up' element={ <SignUpForm /> } />
        <Route path='*' element={ <PageNotFound /> } />
    </Routes>
    </Router>
  );
}

export default App;
