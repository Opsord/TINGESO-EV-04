import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
// Images imports
import ScheduleLogo from "../images/schedule.svg";
// CSS imports
import "../css/Header.css";

function Header() {
  return (
    <>
      <Navbar className="Header">
        <Container className="Header01Content">
          <Navbar.Brand href="/">
            <img alt="" src={ScheduleLogo} className="Logo01" />
            {"  "}
            ELOAM
          </Navbar.Brand>
        </Container>
      </Navbar>
    </>
  );
}

export default Header;
