package hr.java.vjezbe.baza;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.entitet.Stan;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.BazaPodatakaException;

public class BazaPodataka {
	private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);
	private static final String DATABASE_FILE = "database.properties";

	private static Connection spajanjeNaBazu() throws SQLException, IOException {
		Properties svojstva = new Properties();
		svojstva.load(new FileReader(DATABASE_FILE));

		String urlBazePodataka = svojstva.getProperty("bazaPodatakaUrl");
		String korisnickoIme = svojstva.getProperty("korisnickoIme");
		String lozinka = svojstva.getProperty("lozinka");

		Connection veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme, lozinka);

		return veza;
	}

	public static List<Artikl> dohvatiArtikle() throws BazaPodatakaException {
		List<Artikl> listaArtikala = new ArrayList<Artikl>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"SELECT distinct artikl.id as idArtikla, naslov, opis, cijena, snaga, "
							+ "kvadratura, stanje.naziv as stanje, tipArtikla.naziv as tipArtikla "
							+ "FROM artikl inner join " + "stanje on stanje.id = artikl.idStanje inner join "
							+ "tipArtikla on tipArtikla.id = artikl.idTipArtikla");

			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());

			while (resultSet.next()) {
				Artikl artikl = null;
				if (resultSet.getString("tipArtikla").equals("Automobil")) {
					artikl = new Automobil(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							resultSet.getBigDecimal("snaga"), Stanje.valueOf(resultSet.getString("stanje")));
				} else if (resultSet.getString("tipArtikla").equals("Usluga")) {
					artikl = new Usluga(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							Stanje.valueOf(resultSet.getString("stanje")));
				} else if (resultSet.getString("tipArtikla").equals("Stan")) {
					artikl = new Stan(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getInt("kvadratura"),
							resultSet.getBigDecimal("cijena"), Stanje.valueOf(resultSet.getString("stanje")));
				}

				listaArtikala.add(artikl);
			}

		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}

		return listaArtikala;
	}

	public static List<Korisnik> dohvatiKorisnike() throws BazaPodatakaException {
		List<Korisnik> listaKorisnika = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"SELECT distinct korisnik.id as idKorisnika, korisnik.naziv, web, email, "
							+ "telefon, ime, prezime, tipKorisnika.naziv as tipKorisnika " + "from korisnik inner join "
							+ "tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika");

			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());

			while (resultSet.next()) {
				Korisnik korisnik = null;
				if (resultSet.getString("tipKorisnika").equals("PrivatniKorisnik")) {
					korisnik = new PrivatniKorisnik(resultSet.getLong("idKorisnika"), resultSet.getString("ime"),
							resultSet.getString("prezime"), resultSet.getString("email"),
							resultSet.getString("telefon"));
				} else if (resultSet.getString("tipKorisnika").equals("PoslovniKorisnik")) {
					korisnik = new PoslovniKorisnik(resultSet.getLong("idKorisnika"), resultSet.getString("naziv"),
							resultSet.getString("web"), resultSet.getString("telefon"), resultSet.getString("email"));
				}
				listaKorisnika.add(korisnik);
			}

		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}

		return listaKorisnika;
	}

	public static List<Automobil> dohvatiAutePremaKriterijima(Automobil auto) throws BazaPodatakaException {
		List<Automobil> listaAuta = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"SELECT distinct artikl.id, naslov, opis, cijena, snaga, stanje.naziv "
							+ "FROM artikl inner join stanje on stanje.id = artikl.idStanje "
							+ "inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv = 'Automobil'");
			if (auto != null) {
				if (auto.getId() != null)
					sqlUpit.append(" AND artikl.id = " + auto.getId());

				if (auto.getNaslov() != null && !auto.getNaslov().isBlank())
					sqlUpit.append(" AND artikl.naslov LIKE '%" + auto.getNaslov() + "%'");

				if (auto.getOpis() != null && !auto.getOpis().isBlank())
					sqlUpit.append(" AND artikl.opis LIKE '%" + auto.getOpis() + "%'");

				if (auto.getCijena() != null)
					sqlUpit.append(" AND artikl.cijena = " + auto.getCijena());

				if (auto.getSnagaKs() != null)
					sqlUpit.append(" AND artikl.snaga = " + auto.getSnagaKs());
			}

			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());

			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String naslov = resultSet.getString("naslov");
				String opis = resultSet.getString("opis");
				BigDecimal cijena = resultSet.getBigDecimal("cijena");
				BigDecimal snaga = resultSet.getBigDecimal("snaga");
				String stanje = resultSet.getString("naziv");

				Automobil newAuto = new Automobil(id, naslov, opis, snaga, cijena, Stanje.valueOf(stanje));
				listaAuta.add(newAuto);
			}

		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return listaAuta;
	}

	public static void pohraniAutomobil(Automobil auto) throws BazaPodatakaException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza
					.prepareStatement("insert into artikl(Naslov, Opis, Cijena, Snaga, idStanje, idTipArtikla) "
							+ "values (?, ?, ?, ?, ?, 1);");
			preparedStatement.setString(1, auto.getNaslov());
			preparedStatement.setString(2, auto.getOpis());
			preparedStatement.setBigDecimal(3, auto.getCijena());
			preparedStatement.setBigDecimal(4, auto.getSnagaKs());
			preparedStatement.setLong(5, (auto.getStanje().ordinal() + 1));
			preparedStatement.executeUpdate();
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
	}

	public static List<Usluga> dohvatiUslugePremaKriterijima(Usluga usluga) throws BazaPodatakaException {
		List<Usluga> listaUsluga = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder("SELECT distinct artikl.id, naslov, opis, cijena, stanje.naziv "
					+ "FROM artikl inner join stanje on stanje.id = artikl.idStanje "
					+ "inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv = 'Usluga'");
			if (usluga != null) {
				if (usluga.getId() != null)
					sqlUpit.append(" AND artikl.id = " + usluga.getId());

				if (usluga.getNaslov() != null && !usluga.getNaslov().isBlank())
					sqlUpit.append(" AND artikl.naslov LIKE '%" + usluga.getNaslov() + "%'");

				if (usluga.getOpis() != null && !usluga.getOpis().isBlank())
					sqlUpit.append(" AND artikl.opis LIKE '%" + usluga.getOpis() + "%'");

				if (usluga.getCijena() != null)
					sqlUpit.append(" AND artikl.cijena = " + usluga.getCijena());
			}

			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());

			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String naslov = resultSet.getString("naslov");
				String opis = resultSet.getString("opis");
				BigDecimal cijena = resultSet.getBigDecimal("cijena");
				String stanje = resultSet.getString("naziv");

				Usluga newUsluga = new Usluga(id, naslov, opis, cijena, Stanje.valueOf(stanje));
				listaUsluga.add(newUsluga);
			}

		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return listaUsluga;
	}

	public static void pohraniUslugu(Usluga usluga) throws BazaPodatakaException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza.prepareStatement(
					"insert into artikl(Naslov, Opis, Cijena, idStanje, idTipArtikla) " + "values (?, ?, ?, ?, 2);");
			preparedStatement.setString(1, usluga.getNaslov());
			preparedStatement.setString(2, usluga.getOpis());
			preparedStatement.setBigDecimal(3, usluga.getCijena());
			preparedStatement.setLong(4, (usluga.getStanje().ordinal() + 1));
			preparedStatement.executeUpdate();
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
	}

	public static List<Stan> dohvatiStanovePremaKriterijima(Stan stan) throws BazaPodatakaException {
		List<Stan> listaStanova = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"SELECT distinct artikl.id, naslov, opis, cijena, kvadratura, stanje.naziv "
							+ "FROM artikl inner join stanje on stanje.id = artikl.idStanje "
							+ "inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv = 'Stan'");
			if (stan != null) {
				if (stan.getId() != null)
					sqlUpit.append(" AND artikl.id = " + stan.getId());

				if (stan.getNaslov() != null && !stan.getNaslov().isBlank())
					sqlUpit.append(" AND artikl.naslov LIKE '%" + stan.getNaslov() + "%'");

				if (stan.getOpis() != null && !stan.getOpis().isBlank())
					sqlUpit.append(" AND artikl.opis LIKE '%" + stan.getOpis() + "%'");

				if (stan.getCijena() != null)
					sqlUpit.append(" AND artikl.cijena = " + stan.getCijena());

				if (stan.getKvadratura() != 0)
					sqlUpit.append(" AND artikl.kvadratura = " + stan.getKvadratura());
			}

			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());

			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String naslov = resultSet.getString("naslov");
				String opis = resultSet.getString("opis");
				BigDecimal cijena = resultSet.getBigDecimal("cijena");
				int kvadratura = resultSet.getInt("kvadratura");
				String stanje = resultSet.getString("naziv");

				Stan newStan = new Stan(id, naslov, opis, kvadratura, cijena, Stanje.valueOf(stanje));
				listaStanova.add(newStan);
			}

		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return listaStanova;
	}

	public static void pohraniStan(Stan stan) throws BazaPodatakaException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza
					.prepareStatement("insert into artikl(Naslov, Opis, Cijena, Kvadratura, idStanje, idTipArtikla) "
							+ "values (?, ?, ?, ?, ?, 3);");
			preparedStatement.setString(1, stan.getNaslov());
			preparedStatement.setString(2, stan.getOpis());
			preparedStatement.setBigDecimal(3, stan.getCijena());
			preparedStatement.setInt(4, stan.getKvadratura());
			preparedStatement.setLong(5, (stan.getStanje().ordinal() + 1));
			preparedStatement.executeUpdate();
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
	}

	public static List<PrivatniKorisnik> dohvatiPrivatnogKorisnikaPremaKriterijima(PrivatniKorisnik korisnik)
			throws BazaPodatakaException {
		List<PrivatniKorisnik> newKorisnici = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder("SELECT distinct korisnik.id, ime, prezime, email, telefon "
					+ "from korisnik inner join tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika "
					+ "where tipKorisnika.naziv = 'PrivatniKorisnik'");
			if (korisnik != null) {
				if (korisnik.getId() != null)
					sqlUpit.append(" AND korisnik.id = " + korisnik.getId());

				if (korisnik.getIme() != null && !korisnik.getIme().isBlank())
					sqlUpit.append(" AND korisnik.ime LIKE '" + korisnik.getIme() + "%'");

				if (korisnik.getPrezime() != null && !korisnik.getPrezime().isBlank())
					sqlUpit.append(" AND korisnik.prezime LIKE '" + korisnik.getPrezime() + "%'");

				if (korisnik.getEmail() != null && !korisnik.getEmail().isBlank())
					sqlUpit.append(" AND korisnik.email LIKE '%" + korisnik.getEmail() + "%'");

				if (korisnik.getTelefon() != null && !korisnik.getTelefon().isBlank())
					sqlUpit.append(" AND korisnik.telefon LIKE '%" + korisnik.getTelefon() + "%'");
			}

			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());

			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String ime = resultSet.getString("ime");
				String prezime = resultSet.getString("prezime");
				String email = resultSet.getString("email");
				String telefon = resultSet.getString("telefon");

				PrivatniKorisnik newKorisnik = new PrivatniKorisnik(id, ime, prezime, email, telefon);
				newKorisnici.add(newKorisnik);
			}

		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return newKorisnici;
	}

	public static void pohraniPrivatnogKorisnika(PrivatniKorisnik korisnik) throws BazaPodatakaException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza.prepareStatement(
					"insert into korisnik(Ime, Prezime, Email, Telefon, idTipKorisnika) " + "values (?, ?, ?, ?, 1);");
			preparedStatement.setString(1, korisnik.getIme());
			preparedStatement.setString(2, korisnik.getPrezime());
			preparedStatement.setString(3, korisnik.getEmail());
			preparedStatement.setString(4, korisnik.getTelefon());
			preparedStatement.executeUpdate();
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
	}

	public static List<PoslovniKorisnik> dohvatiPoslovnogKorisnikaPremaKriterijima(PoslovniKorisnik korisnik)
			throws BazaPodatakaException {
		List<PoslovniKorisnik> newKorisnici = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"SELECT distinct korisnik.id, korisnik.naziv, web, email, telefon "
							+ "from korisnik inner join tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika "
							+ "where tipKorisnika.naziv = 'PoslovniKorisnik'");
			if (korisnik != null) {
				if (korisnik.getId() != null)
					sqlUpit.append(" AND korisnik.id = " + korisnik.getId());

				if (korisnik.getNaziv() != null && !korisnik.getNaziv().isBlank())
					sqlUpit.append(" AND korisnik.naziv LIKE '" + korisnik.getNaziv() + "%'");

				if (korisnik.getWeb() != null && !korisnik.getWeb().isBlank())
					sqlUpit.append(" AND korisnik.web LIKE '" + korisnik.getWeb() + "%'");

				if (korisnik.getEmail() != null && !korisnik.getEmail().isBlank())
					sqlUpit.append(" AND korisnik.email LIKE '%" + korisnik.getEmail() + "%'");

				if (korisnik.getTelefon() != null && !korisnik.getTelefon().isBlank())
					sqlUpit.append(" AND korisnik.telefon LIKE '%" + korisnik.getTelefon() + "%'");
			}

			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());

			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String naziv = resultSet.getString("naziv");
				String web = resultSet.getString("web");
				String email = resultSet.getString("email");
				String telefon = resultSet.getString("telefon");

				PoslovniKorisnik newKorisnik = new PoslovniKorisnik(id, naziv, email, web, telefon);
				newKorisnici.add(newKorisnik);
			}

		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return newKorisnici;
	}

	public static void pohraniPoslovnogKorisnika(PoslovniKorisnik korisnik) throws BazaPodatakaException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza.prepareStatement(
					"insert into korisnik(Naziv, Web, Email, Telefon, idTipKorisnika) " + "values (?, ?, ?, ?, 2);");
			preparedStatement.setString(1, korisnik.getNaziv());
			preparedStatement.setString(2, korisnik.getWeb());
			preparedStatement.setString(3, korisnik.getEmail());
			preparedStatement.setString(4, korisnik.getTelefon());
			preparedStatement.executeUpdate();
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
	}

	public static List<Prodaja> dohvatiProdajuPremaKriterijima(Prodaja prodaja) throws BazaPodatakaException {
		List<Prodaja> listaProdaje = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"select distinct korisnik.id as idKorisnika, tipKorisnika.naziv as tipKorisnika, "
							+ "korisnik.naziv as nazivKorisnika, web, email, telefon, "
							+ "korisnik.ime, korisnik.prezime, tipArtikla.naziv as tipArtikla, "
							+ "artikl.naslov, artikl.opis, artikl.cijena, artikl.kvadratura, "
							+ "artikl.snaga, stanje.naziv as stanje, prodaja.datumObjave, artikl.id as idArtikla "
							+ "from korisnik inner join "
							+ "tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika inner join "
							+ "prodaja on prodaja.idKorisnik = korisnik.id inner join "
							+ "artikl on artikl.id = prodaja.idArtikl inner join "
							+ "tipArtikla on tipArtikla.id = artikl.idTipArtikla inner join "
							+ "stanje on stanje.id = artikl.idStanje where 1=1");
			if (prodaja != null) {
				if (prodaja.getArtikl() != null)
					sqlUpit.append(" AND prodaja.idArtikl = " + prodaja.getArtikl().getId());

				if (prodaja.getKorisnik() != null)
					sqlUpit.append(" AND prodaja.idKorisnik = " + prodaja.getKorisnik().getId());

				if (prodaja.getDatumObjave() != null) {
					sqlUpit.append(" AND prodaja.datumObjave = '"
							+ prodaja.getDatumObjave().format(DateTimeFormatter.ISO_DATE) + "'");
				}
			}

			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());

			while (resultSet.next()) {
				Korisnik korisnik = null;
				if (resultSet.getString("tipKorisnika").equals("PrivatniKorisnik")) {
					korisnik = new PrivatniKorisnik(resultSet.getLong("idKorisnika"), resultSet.getString("ime"),
							resultSet.getString("prezime"), resultSet.getString("email"),
							resultSet.getString("telefon"));
				} else if (resultSet.getString("tipKorisnika").equals("PoslovniKorisnik")) {
					korisnik = new PoslovniKorisnik(resultSet.getLong("idKorisnika"),
							resultSet.getString("nazivKorisnika"), resultSet.getString("web"),
							resultSet.getString("telefon"), resultSet.getString("email"));
				}

				Artikl artikl = null;
				if (resultSet.getString("tipArtikla").equals("Automobil")) {
					artikl = new Automobil(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							resultSet.getBigDecimal("snaga"), Stanje.valueOf(resultSet.getString("stanje")));
				} else if (resultSet.getString("tipArtikla").equals("Usluga")) {
					artikl = new Usluga(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							Stanje.valueOf(resultSet.getString("stanje")));
				} else if (resultSet.getString("tipArtikla").equals("Stan")) {
					artikl = new Stan(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getInt("kvadratura"),
							resultSet.getBigDecimal("cijena"), Stanje.valueOf(resultSet.getString("stanje")));
				}

				LocalDate localDate = resultSet.getTimestamp("datumObjave").toLocalDateTime().toLocalDate();

				Prodaja novaProdaja = new Prodaja(artikl, korisnik, localDate);
				listaProdaje.add(novaProdaja);
			}

		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}

		return listaProdaje;
	}

	public static void pohraniProdaju(Prodaja prodaja) throws BazaPodatakaException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza
					.prepareStatement("insert into prodaja(idArtikl, idKorisnik, datumObjave) " + "values (?, ?, ?);");
			preparedStatement.setLong(1, prodaja.getArtikl().getId());
			preparedStatement.setLong(2, prodaja.getKorisnik().getId());
			preparedStatement.setDate(3, Date.valueOf(prodaja.getDatumObjave()));
			preparedStatement.executeUpdate();
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
	}

	public static Prodaja dohvatiZadnjuProdaju() throws BazaPodatakaException {
		Prodaja prodaja = null;
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"select distinct korisnik.id as idKorisnika, tipKorisnika.naziv as tipKorisnika, "
							+ "korisnik.naziv as nazivKorisnika, web, email, telefon, "
							+ "korisnik.ime, korisnik.prezime, tipArtikla.naziv as tipArtikla, "
							+ "artikl.naslov, artikl.opis, artikl.cijena, artikl.kvadratura, "
							+ "artikl.snaga, stanje.naziv as stanje, prodaja.datumObjave, " + "artikl.id as idArtikla "
							+ "from korisnik inner join "
							+ "tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika inner join "
							+ "prodaja on prodaja.idKorisnik = korisnik.id inner join "
							+ "artikl on artikl.id = prodaja.idArtikl inner join "
							+ "tipArtikla on tipArtikla.id = artikl.idTipArtikla inner join "
							+ "stanje on stanje.id = artikl.idStanje " + "order by datumObjave desc " + "limit 1");

			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());

			resultSet.next();

			Korisnik korisnik = null;
			if (resultSet.getString("tipKorisnika").equals("PrivatniKorisnik")) {
				korisnik = new PrivatniKorisnik(resultSet.getLong("idKorisnika"), resultSet.getString("ime"),
						resultSet.getString("prezime"), resultSet.getString("email"), resultSet.getString("telefon"));
			} else if (resultSet.getString("tipKorisnika").equals("PoslovniKorisnik")) {
				korisnik = new PoslovniKorisnik(resultSet.getLong("idKorisnika"), resultSet.getString("nazivKorisnika"),
						resultSet.getString("web"), resultSet.getString("telefon"), resultSet.getString("email"));
			}

			Artikl artikl = null;
			if (resultSet.getString("tipArtikla").equals("Automobil")) {
				artikl = new Automobil(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
						resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
						resultSet.getBigDecimal("snaga"), Stanje.valueOf(resultSet.getString("stanje")));
			} else if (resultSet.getString("tipArtikla").equals("Usluga")) {
				artikl = new Usluga(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
						resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
						Stanje.valueOf(resultSet.getString("stanje")));
			} else if (resultSet.getString("tipArtikla").equals("Stan")) {
				artikl = new Stan(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
						resultSet.getString("opis"), resultSet.getInt("kvadratura"), resultSet.getBigDecimal("cijena"),
						Stanje.valueOf(resultSet.getString("stanje")));
			}

			LocalDate localDate = resultSet.getTimestamp("datumObjave").toLocalDateTime().toLocalDate();

			prodaja = new Prodaja(artikl, korisnik, localDate);

		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return prodaja;
	}

	public static Usluga dohvatiZadnjuUslugu() throws BazaPodatakaException {
		Usluga usluga = null;
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder("SELECT distinct artikl.id, naslov, opis, cijena, stanje.naziv "
					+ "FROM artikl inner join stanje on stanje.id = artikl.idStanje "
					+ "inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv = 'Usluga' order by id desc limit 1");

			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());

			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String naslov = resultSet.getString("naslov");
				String opis = resultSet.getString("opis");
				BigDecimal cijena = resultSet.getBigDecimal("cijena");
				String stanje = resultSet.getString("naziv");

				usluga = new Usluga(id, naslov, opis, cijena, Stanje.valueOf(stanje));
			}

		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return usluga;
	}
}
