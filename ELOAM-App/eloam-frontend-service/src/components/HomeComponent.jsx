import React from "react";
// Components imports
import HeaderComponent from "./HeaderComponent";
import CustomCard from "./CardComponent";
import Footer from "./FooterComponent";
// CSS imports
import "../css/HomeComponent.css";

const cardConfigTeachers = {
  title: "Administracion de profesores",
  bodyContent:
    "Listado y administracion de profesores.",
  imageUrl: "https://picsum.photos/200/100",
  buttonLabel: "Ir a profesores",
  linkto: "/teachers",
};

const cardConfigEquipment = {
  title: "Administracion de equipos",
  bodyContent:
    "Listado y administracion de equipos disponibles.",
  imageUrl: "https://picsum.photos/200/100",
  buttonLabel: "Ir a equipos",
  linkTo: "/equipment",
};

const cardConfigLoans = {
  title: "Administracion de prestamos",
  bodyContent:
    "Listado y administracion de prestamos.",
  imageUrl: "https://picsum.photos/200/100",
  buttonLabel: "Ir a prestamos",
  linkTo: "/loans",
};

const HomeComponent = () => {
  return (
    <div className="MainDiv">
      <div className="Header">
        <HeaderComponent />
      </div>

      <div className="PageBody">
        <div className="OptionsContainer">
          <a href={cardConfigTeachers.linkto}>
            <CustomCard cardConfig={cardConfigTeachers} />
          </a>
          <a href={cardConfigEquipment.linkTo}>
            <CustomCard cardConfig={cardConfigEquipment} />
          </a>
          <a href={cardConfigLoans.linkTo}>
            <CustomCard cardConfig={cardConfigLoans} />
          </a>
        </div>
      </div>

      <div className="Footer">
        <Footer />
      </div>
    </div>
  );
};

export default HomeComponent;
