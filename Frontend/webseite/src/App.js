import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import './App.css';
import Footer from './Components/Footer/Footer';
import NavBar from './Components/Navbar/NavBar';
import AdminRoute from './context/AdminRoute';
import { AuthProvider } from './context/AuthProvider';
import AdminBereich from './Seiten/Admin/AdminBereich';
import AdminBuchungen from './Seiten/Admin/AdminBuchungen';
import ArtikelBearbeiten from './Seiten/Artikel/ArtikelBearbeiten';
import ArtikelDetail from './Seiten/Artikel/ArtikelDetail';
import ArtikelEinfügen from './Seiten/Artikel/ArtikelEinfügen';
import BuchungBestätigung from './Seiten/Artikel/BuchungBestätigung';
import BuchungFormular from './Seiten/Artikel/BuchungFormular';
import UserKonto from './Seiten/Kunde/UserKonto';
import Impressum from './Seiten/Shop/Impressum';
import Login from './Seiten/Shop/Login';
import Register from './Seiten/Shop/Register';
import Startseite from './Seiten/Shop/Startseite';


function App() {
  return (
    <>
      <AuthProvider>
        <Router>
          <div className="app-container">
            <NavBar />
            <main className="content">
              <Routes>
                <Route path='/admin' element={<AdminRoute><AdminBereich /></AdminRoute>} />
                <Route path="/" element={<Startseite />} />
                <Route path="/impressum" element={<Impressum />} />
                <Route path="/einloggen" element={<Login />} />
                <Route path="/mein_konto" element={<UserKonto />} />
                <Route path="/registrieren" element={<Register />} />
                <Route path="/mietartikel/:id" element={<ArtikelDetail />} />
                <Route path="/admin/artikel/:id/bearbeiten" element={<AdminRoute><ArtikelBearbeiten /> </AdminRoute>} />
                <Route path='/mietartikel/:id/formular' element={<BuchungFormular />} />
                <Route path="/admin/neu_artikel_einstellen" element={<AdminRoute><ArtikelEinfügen /></AdminRoute>} />
                <Route path="/admin/buchungen_details" element={<AdminRoute><AdminBuchungen /> </AdminRoute>} />
                <Route path='/buchungsbestaetigung' element={<BuchungBestätigung />} />
              </Routes>
            </main>
            <Footer />
          </div>
        </Router>
      </AuthProvider>
    </>
  );
}

export default App;
