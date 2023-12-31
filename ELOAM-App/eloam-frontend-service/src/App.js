import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
// Components imports
import HomeComponent from "./components/HomeComponent";
import TeacherComponent from "./components/TeacherListComponent";
import EquipmentComponent from "./components/EquipmentListComponent";
import LoanComponent from "./components/LoanListComponent";
import LoanCreatorComponent from "./components/LoanCreatorComponent";

function App() {
  return (
    <div>
      <Router>
        <Routes>
          {/* Home page */}
          <Route path="/" element={<HomeComponent />} />
          {/* Teacher section */}
          <Route path="/teachers" element={<TeacherComponent />} />
          {/* Equipment section */}
          <Route path="/equipment" element={<EquipmentComponent />} />
          {/* Loan section */}
          <Route path="/loans" element={<LoanComponent />} />
          {/* Loan creation */}
          <Route path="/loans/create" element={<LoanCreatorComponent />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
