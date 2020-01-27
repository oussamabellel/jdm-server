package com.example.demo.metier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.example.demo.entity.Mot;
import com.example.demo.entity.Noeud;
import com.example.demo.entity.NoeudType;
import com.example.demo.entity.Relation;
import com.example.demo.entity.RelationType;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class Methods {

	public final ArrayList<Integer> relationsToIgnore = new ArrayList<Integer>() {
		{
			add(4);
			add(12);
			add(18);
			add(19);
			add(29);
			add(33);
			add(36);
			add(45);
			add(46);
			add(47);
			add(48);
			add(66);
			add(118);
			add(128);
			add(200);
			add(444);
			add(555);
			add(1000);
			add(1001);
			add(1002);
			add(2001);
		}
	};

	public String getHTML(String mot) throws Exception {

		String uncoded = URLEncoder.encode(mot, "ISO-8859-1");
		String lien = "http://www.jeuxdemots.org/rezo-dump.php?gotermsubmit=Chercher&gotermrel=" + uncoded + "&rel=";
		StringBuilder result = new StringBuilder();
		URL url = new URL(lien);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "ISO-8859-1"));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line + "\n");
		}
		rd.close();
		return result.toString();
	}

	public String getHTMLSpecific(String mot) throws Exception {

		String uncoded = URLEncoder.encode(mot, "ISO-8859-1");
		String lien = "http://www.jeuxdemots.org/rezo-dump.php?gotermsubmit=Chercher&gotermrel=" + uncoded
				+ "&rel=&relout=norelout";
		StringBuilder result = new StringBuilder();
		URL url = new URL(lien);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "ISO-8859-1"));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line + "\n");
		}
		rd.close();
		return result.toString();
	}

	public ArrayList<NoeudType> getNoeudType(String data) {

		ArrayList<NoeudType> liste = new ArrayList<NoeudType>();
		String[] split = data.split("\n");
		String[] myArray = Arrays.copyOfRange(split, 2, split.length);

		for (String yourString : myArray) {

			String[] elements = yourString.split(";");
			liste.add(new NoeudType(Integer.parseInt(elements[1]), elements[2].substring(1, elements[2].length() - 1)));
		}

		return liste;
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional '-' and decimal.
	}

	public ArrayList<Noeud> getNoeuds(String data, String noeudtypes) {

		ArrayList<Noeud> liste = new ArrayList<Noeud>();
		String[] test = data.split("\n");
		String[] myArray = Arrays.copyOfRange(test, 2, test.length);
		int integer;
		String nameToSend;

		for (String edge : myArray) {
			String elements[] = edge.split(";");
			if (elements.length > 5) {
				// nameToSend = elements[5].substring(1, elements[5].length() - 1);
				nameToSend = elements[5].replace("'", "");
			} else {

				nameToSend = elements[2].replace("'", "");
				// nameToSend = elements[2].substring(1, elements[2].length() - 1);
			}

			if (!isNumeric(elements[4])) {
				integer = 0;
			} else {
				Scanner in = new Scanner(elements[4]).useDelimiter("[^0-9]+");
				integer = in.nextInt();
			}

			liste.add(new Noeud(Integer.parseInt(elements[1]), nameToSend, integer));
		}

		return liste;

	}

	public ArrayList<RelationType> getRelationTypes(String data) {

		ArrayList<RelationType> liste = new ArrayList<RelationType>();
		String[] split = data.split("\n");
		String[] yourArray = Arrays.copyOfRange(split, 2, split.length);

		for (String Rt : yourArray) {
			String elements[] = Rt.split(";");
			int id = Integer.parseInt(elements[1]);
			if (relationsToIgnore.contains(id)) {
				continue;
			} else {
				liste.add(new RelationType(id, elements[2].substring(1, elements[2].length() - 1)));
			}

		}
		return liste;
	}

	public ArrayList<Relation> getContentRelation(String data, ArrayList<Noeud> noeuds, ArrayList<RelationType> liste,
			boolean is_out) {

		ArrayList<Relation> liste_relation = new ArrayList<Relation>();
		String[] split = data.split("\n");
		if (split[0].equals(" END")) {
			liste_relation = null;
		} else {

			if (split.length > 2) {
				String[] yourArray = Arrays.copyOfRange(split, 2, split.length);
				if (is_out) {

					for (String yourString : yourArray) {

						String elements[] = yourString.split(";");
						Scanner in = new Scanner(elements[5]).useDelimiter("(?>[^-0-9]+|-(?![0-9]))+|(?=-[0-9])");
						int integer = in.nextInt();
						Noeud nd = getNoeudInformation(Integer.parseInt(elements[3]), noeuds);
						RelationType rt = getRelationTypeInformation(Integer.parseInt(elements[4]), liste);
						if (nd != null && rt != null) {
							liste_relation.add(new Relation(Integer.parseInt(elements[1]), nd, rt, integer));
						} else {
							continue;
						}

					}

				} else {

					for (String yourString : yourArray) {

						String elements[] = yourString.split(";");
						Scanner in = new Scanner(elements[5]).useDelimiter("(?>[^-0-9]+|-(?![0-9]))+|(?=-[0-9])");
						int integer = in.nextInt();
						Noeud nd = getNoeudInformation(Integer.parseInt(elements[2]), noeuds);
						RelationType rt = getRelationTypeInformation(Integer.parseInt(elements[4]), liste);

						if (nd != null && rt != null) {
							liste_relation.add(new Relation(Integer.parseInt(elements[1]), nd, rt, integer));
						} else {
							continue;
						}

					}

				}
			}

		}
		return liste_relation;

	}

	public NoeudType getNoeudTypeInformation(int id, ArrayList<NoeudType> liste) {

		NoeudType noeudType = null;
		for (NoeudType typenoeud : liste) {
			if (typenoeud.getId() == id) {
				noeudType = new NoeudType(typenoeud.getId(), typenoeud.getNom());
			}
		}
		return noeudType;
	}

	public RelationType getRelationTypeInformation(int id, ArrayList<RelationType> liste) {

		RelationType relationType = null;
		for (RelationType typerelation : liste) {

			if (typerelation.getId() == id) {
				relationType = new RelationType(typerelation.getId(), typerelation.getName(),
						typerelation.getTrgpname(), typerelation.getRthelp());
			}
		}
		return relationType;
	}

	public Noeud getNoeudInformation(int id, ArrayList<Noeud> Noeuds) {
		Noeud noeud = null;
		for (Noeud nd : Noeuds) {
			if (nd.getId() == id && !nd.getNom().startsWith("_")) {
				noeud = nd;
			}
		}
		return noeud;
	}

	public ArrayList<String> getDefinition(String[] data) {

		ArrayList<String> definitions = new ArrayList<String>();
		String[] yourArray = Arrays.copyOfRange(data, 1, data.length);
		for (String string : yourArray) {

			String def = string.replaceAll("<br /> +[0-9]", "").replace("<br />", "");
			if (Character.isDigit(def.charAt(0))) {
				def = def.substring(3, def.length());
			}

			definitions.add(def);

		}
		return definitions;
	}

	public Mot FilterResult(Mot mot, String relation) {

		Map<String, List<Relation>> mapEntantres = new HashMap<String, List<Relation>>();
		Map<String, List<Relation>> mapSortantes = new HashMap<String, List<Relation>>();
		Map<String, List<Relation>> MotmapEntrantes = mot.getMapEntrantes();
		Map<String, List<Relation>> MotmapSortantes = mot.getMapSortantes();

		if (MotmapEntrantes != null) {
			for (Map.Entry<String, List<Relation>> entry : MotmapEntrantes.entrySet()) {
				for (Relation r : entry.getValue()) {
					if (r == null) {
						continue;
					}
					if (r.getType().getId() == Integer.parseInt(relation)) {
						mapEntantres.put(entry.getKey(), MotmapEntrantes.get(entry.getKey()));
					}
				}
			}
		}

		if (MotmapSortantes != null) {
			for (Map.Entry<String, List<Relation>> entry : MotmapSortantes.entrySet()) {
				for (Relation r : entry.getValue()) {
					if (r == null) {
						continue;
					}
					if (r.getType().getId() == Integer.parseInt(relation)) {
						mapSortantes.put(entry.getKey(), MotmapSortantes.get(entry.getKey()));
					}
				}
			}

		}
		mot.setMapEntrantes(mapEntantres);
		mot.setMapSortantes(mapSortantes);

		return mot;

	}

	public ArrayList<String> getRaffinement(Map<String, List<Relation>> map) throws Exception {

		ArrayList<String> raf_definitions = new ArrayList<String>();

		String raf = "r_raff_sem";
		if (map.containsKey(raf)) {
			List<Relation> relations = map.get(raf);
			for (Relation r : relations) {
				String mot = r.getNoeud().getNom();
				String html = getHTML(mot);

				if (!html.contains("<CODE>")) {
					continue;
				}
				String[] split = html.split("<CODE>");
				String[] split2 = split[1].split("</CODE>");
				String[] splitx = split2[0].split("<def>");
				if (splitx.length > 1) {

					String[] splitx2 = splitx[1].split("</def>");
					String[] splitx3 = splitx2[0].split("<br />\n");

					if (splitx3.length >= 2) {
						ArrayList<String> defs = getDefinition(splitx3);
						raf_definitions.addAll(defs);
					}
				}

			}
		}
		return raf_definitions;
	}

	public Map<String, List<Relation>> TryGetRelations(String mot) throws Exception {
		System.out.println("trying to find relations");
		String html = getHTMLSpecific(mot);
		String[] split = html.split("<CODE>");
		String[] split2 = split[1].split("</CODE>");
		String[] splitx = split2[0].split("<def>");

		if (splitx != null) {
			String[] splitx2 = splitx[1].split("</def>");
			String[] split3 = splitx2[1].split("\n//");
			String relations_types = split3[3];
			String relation_entrante = split3[4];
			String les_noeuds = split3[2];
			String noeud_types = split3[1];

			ArrayList<RelationType> relationTypes = getRelationTypes(relations_types);
			ArrayList<Noeud> Noeuds = getNoeuds(les_noeuds, noeud_types);

			ArrayList<Relation> relations_entrantes = getContentRelation(relation_entrante, Noeuds, relationTypes,
					false);
			if (relations_entrantes != null) {
				return SortRelations(relations_entrantes);
			}
		}

		return null;
	}

	public Mot Parser(String word, String relation) {

		Mot mot = null;
		Gson gson = new Gson();
		File f = new File("./newcache/" + word + ".txt");
		if (f.exists()) {

			JsonReader reader;
			try {
				reader = new JsonReader(new FileReader("./newcache/" + word + ".txt"));
				mot = gson.fromJson(reader, Mot.class);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			ArrayList<String> raf_definitions;
			ArrayList<String> definiton = null;
			ArrayList<Relation> relations_entrantes;
			ArrayList<Relation> relations_sortantes;
			Map<String, List<Relation>> mapEntantres = null;
			Map<String, List<Relation>> mapSortantes = null;
			String[] splitx2 = null;

			String html;
			try {
				html = getHTML(word);
				if (!html.contains("<CODE>")) {
					return new Mot(0, word, null, 0, null, null, null, null, null);
				}
				String[] split = html.split("<CODE>");
				String[] split2 = split[1].split("</CODE>");
				String[] splitx = split2[0].split("<def>");
				if (splitx.length > 1) {
					splitx2 = splitx[1].split("</def>");
					String[] splitx3 = splitx2[0].split("<br />\n");
					if (splitx3.length >= 2) {
						definiton = getDefinition(splitx3);

					}
				}

				String[] split3 = splitx2[1].split("\n//");
				String noeud_types = split3[1];
				String les_noeuds = split3[2];
				String relations_types = split3[3];
				String relation_sortantes = split3[4];
				String relation_entrante = split3[5];

				ArrayList<RelationType> relationTypes = getRelationTypes(relations_types);
				ArrayList<Noeud> Noeuds = getNoeuds(les_noeuds, noeud_types);

				int idMot = Noeuds.get(0).getId();
				String nom = Noeuds.get(0).getNom();
				NoeudType noeudType = Noeuds.get(0).getType();
				int poid = Noeuds.get(0).getPoids();
				String formatted_name = Noeuds.get(0).getMotFormate();

				relations_entrantes = getContentRelation(relation_entrante, Noeuds, relationTypes, false);

				if (relations_entrantes != null) {
					mapEntantres = SortRelations(relations_entrantes);

				} else {
					mapEntantres = TryGetRelations(word);
				}

				relations_sortantes = getContentRelation(relation_sortantes, Noeuds, relationTypes, true);
				if (relations_sortantes != null) {
					mapSortantes = SortRelations(relations_sortantes);

				} else {

					mapSortantes = null;
				}

				raf_definitions = getRaffinement(mapSortantes);
				mot = new Mot(idMot, nom, noeudType, poid, formatted_name, definiton, mapEntantres, mapSortantes,
						raf_definitions);
				saveInCache(word, mot, gson);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (relation != null && !relation.isEmpty()) {
			mot = FilterResult(mot, relation);
		}

		return mot;

	}

	private Map<String, List<Relation>> SortRelations(ArrayList<Relation> relations_entrantes) {

		// RemoveUnders(relations_entrantes);

		return relations_entrantes.stream()
				.sorted(Comparator.comparing(Relation::getPoids).reversed().thenComparing(rl -> rl.noeud.nom))
				.collect(Collectors.groupingBy(ch -> ch.type.name));
	}

	private void RemoveUnders(ArrayList<Relation> relations_entrantes) {
		Iterator<Relation> it = relations_entrantes.iterator();

		while (it.hasNext()) {
			Relation e = it.next();

			if (e.getNoeud().getNom().startsWith("_")) {
				it.remove();
			}
		}
	}

	public void saveInCache(String word, Mot mot, Gson gson) {
		String json = gson.toJson(mot);

		try (FileWriter file = new FileWriter("./newcache/" + word + ".txt")) {

			file.write(json);
			file.flush();
			System.out.println("saved cache");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
