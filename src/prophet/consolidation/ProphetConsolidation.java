/*
 * Programming notes:
 * Sort by company before running.
 * Need to finish:
 *      Handle null objects in company
 *      Combine notes into output
 *      Write secondary output to excel?
 */
package prophet.consolidation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Joe Licata
 */
public class ProphetConsolidation {

    public static void main(String[] args) {
        ArrayList<Company> companies = new ArrayList();
        ArrayList<Contact> contacts = new ArrayList();
        ArrayList<Opportunity> opportunities = new ArrayList();
        ArrayList<OpportunityNote> notes = new ArrayList();

        companies = parseCompaniesExcel(companies, "company.xlsx");
        contacts = parseContactsExcel(contacts, "contact.xlsx");
        opportunities = parseOpportunitiesExcel(opportunities, "opportunity info.xlsx");
        notes = parseOpportunityNotesExcel(notes, "opportunity notes.xlsx");

        companies = combineContactsCompany(companies, contacts);
        companies = combineOpportunityCompany(companies, opportunities);
        companies = combineOpportunityNotesCompany(companies, notes);

        output(companies);
    }

    public static ArrayList combineContactsCompany(ArrayList<Company> companies, ArrayList<Contact> contacts) {
        int contactsCombined = 0;
        int j;
        for (int i = 0; i < companies.size(); i++) {
            j = contactsCombined;
            while (j < contacts.size()) {
                if (companies.get(i).getName().equals(contacts.get(j).getCompany())) {
                    companies.get(i).getContacts().add(contacts.get(j));
                    contactsCombined++;
                }
                j++;
            }
        }
        return companies;
    }

    public static ArrayList combineOpportunityCompany(ArrayList<Company> companies, ArrayList<Opportunity> opportunities) {
        int opportunitiesCombined = 0;
        int j;
        for (int i = 0; i < companies.size(); i++) {
            j = opportunitiesCombined;
            while (j < opportunities.size()) {
                if (companies.get(i).getName().equals(opportunities.get(j).getCompany())) {
                    companies.get(i).setOpportunity(opportunities.get(j));
                    opportunitiesCombined++;
                }
                j++;
            }
        }
        return companies;
    }

    public static ArrayList combineOpportunityNotesCompany(ArrayList<Company> companies, ArrayList<OpportunityNote> notes) {
        int notesCombined = 0;
        int j;

        for (int i = 0; i < companies.size(); i++) {
            j = notesCombined;
            while (j < notes.size()) {
                if (companies.get(i).getName().equals(notes.get(j).getCompanyName())) {
                    companies.get(i).getOpportunityNotes().add(notes.get(j).getNote());
                    notesCombined++;
                }
                j++;
            }
            //Output to file here after company is finished and dereference company in arraylist?
        }
        return companies;
    }

    public static void output(ArrayList<Company> companies) {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new File("output.csv"));

            StringBuilder sb = new StringBuilder();
            sb.append("Company Name,Address,City,State,Zip Code,Phone Number,Website,Opportunity Description,");
            sb.append("Sales Stage,Sales Priority,Sales Type,Service Type,Sales Revenue,Recur Revenue, Last Updated");
            sb.append("Contact Name,Phone Number,Cell Phon,Home Phone,Fax,Email,Website,Contact Notes\n");
            for (Company company : companies) {
                ArrayList<Contact> contacts = company.getContacts();
                Opportunity opportunity = company.getOpportunity();

                sb.append(company.getName());
                sb.append(',');
                sb.append(company.getAddress());
                sb.append(',');
                sb.append(company.getCity());
                sb.append(',');
                sb.append(company.getState());
                sb.append(',');
                sb.append(company.getZip());
                sb.append(',');
                sb.append(company.getPhone());
                sb.append(',');
                sb.append(company.getWebsite());
                sb.append(',');
                //add opportunity
                sb.append(opportunity.getDescription());
                sb.append(',');
                sb.append(opportunity.getSalesStage());
                sb.append(',');
                sb.append(opportunity.getSalesPriority());
                sb.append(',');
                sb.append(opportunity.getSalesType());
                sb.append(',');
                sb.append(opportunity.getServiceType());
                sb.append(',');
                sb.append(opportunity.getSalesRevenue());
                sb.append(',');
                sb.append(opportunity.getRecurRevenue());
                sb.append(',');
                sb.append(opportunity.getLastUpdated());
                sb.append(',');
                //add first contact and note
                //add if array > 1 while loop else put proper commas
                //end with sb.append('\n');
                if (contacts.size() > 0) {
                    sb.append(contacts.get(0).getName());
                    sb.append(',');
                    sb.append(contacts.get(0).getPhone());
                    sb.append(',');
                    sb.append(contacts.get(0).getCellPhone());
                    sb.append(',');
                    sb.append(contacts.get(0).getHomePhone());
                    sb.append(',');
                    sb.append(contacts.get(0).getFax());
                    sb.append(',');
                    sb.append(contacts.get(0).getEmail());
                    sb.append(',');
                    sb.append(contacts.get(0).getWebsite());
                    sb.append(',');
                    sb.append(contacts.get(0).getContactNotes());
                    sb.append('\n');
                } else {
                    sb.append(" , , , , , , ,\n");
                }
                if (contacts.size() > 1) {
                    for (int i = 1; i < contacts.size(); i++) {
                        sb.append(" , , , , , , , , , , , , , , ,");
                        sb.append(contacts.get(i).getName());
                        sb.append(',');
                        sb.append(contacts.get(i).getPhone());
                        sb.append(',');
                        sb.append(contacts.get(i).getCellPhone());
                        sb.append(',');
                        sb.append(contacts.get(i).getHomePhone());
                        sb.append(',');
                        sb.append(contacts.get(i).getFax());
                        sb.append(',');
                        sb.append(contacts.get(i).getEmail());
                        sb.append(',');
                        sb.append(contacts.get(i).getWebsite());
                        sb.append(',');
                        sb.append(contacts.get(i).getContactNotes());
                        sb.append('\n');
                    }
                }
            }
            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException ex) {
        }
    }

    public static ArrayList<Company> parseCompaniesExcel(ArrayList<Company> companies, String filename) {
        try {
            FileInputStream file = new FileInputStream(new File(filename));
            Workbook wb = new XSSFWorkbook(file);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            String[] input;
            int i;
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cells = currentRow.iterator();
                input = new String[8];
                i = 0;
                while (cells.hasNext()) {
                    Cell currentCell = cells.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        input[i] = cleanString(new DataFormatter().formatCellValue(currentCell));
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        input[i] = cleanString(String.valueOf(new DataFormatter().formatCellValue(currentCell)));
                    }
                    i++;
                }
                Company co = new Company();
                co.setName(input[0]);
                co.setAddress(input[1]);
                co.setCity(input[2]);
                co.setState(input[3]);
                co.setZip(input[4]);
                co.setPhone(input[5]);
                co.setWebsite(input[6]);
                co.setCategory(input[7]);
                companies.add(co);
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        return companies;
    }

    public static ArrayList<Contact> parseContactsExcel(ArrayList<Contact> contacts, String filename) {
        try {
            FileInputStream file = new FileInputStream(new File(filename));
            Workbook wb = new XSSFWorkbook(file);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            String[] input;
            int i;
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cells = currentRow.iterator();
                input = new String[9];
                i = 0;
                while (cells.hasNext()) {
                    Cell currentCell = cells.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        input[i] = cleanString(new DataFormatter().formatCellValue(currentCell));
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        input[i] = cleanString(String.valueOf(new DataFormatter().formatCellValue(currentCell)));
                    }
                    i++;
                }
                Contact co = new Contact();
                co.setName(input[0]);
                co.setCompany(input[1]);
                co.setPhone(input[2]);
                co.setHomePhone(input[3]);
                co.setCellPhone(input[4]);
                co.setFax(input[5]);
                co.setEmail(input[6]);
                co.setWebsite(input[7]);
                if (input[8] != null) {
                    co.setContactNotes(input[8]);
                } else {
                    co.setContactNotes("No contact notes available.");
                }
                contacts.add(co);
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        return contacts;
    }

    public static ArrayList<Opportunity> parseOpportunitiesExcel(ArrayList<Opportunity> opportunities, String filename) {
        try {
            FileInputStream file = new FileInputStream(new File(filename));
            Workbook wb = new XSSFWorkbook(file);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            String[] input;
            int i;
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cells = currentRow.iterator();
                input = new String[9];
                i = 0;
                while (cells.hasNext()) {
                    Cell currentCell = cells.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        input[i] = cleanString(new DataFormatter().formatCellValue(currentCell));
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        input[i] = cleanString(String.valueOf(new DataFormatter().formatCellValue(currentCell)));
                    }
                    i++;
                }
                Opportunity co = new Opportunity();
                co.setCompany(input[0]);
                co.setDescription(input[1]);
                co.setSalesStage(input[2]);
                co.setSalesPriority(input[3]);
                co.setSalesType(input[4]);
                co.setServiceType(input[5]);
                co.setSalesRevenue(input[6]);
                co.setRecurRevenue(input[7]);
                co.setLastUpdated(input[8]);
                opportunities.add(co);
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        return opportunities;
    }

    public static ArrayList<OpportunityNote> parseOpportunityNotesExcel(ArrayList<OpportunityNote> notes, String filename) {
        try {
            FileInputStream file = new FileInputStream(new File(filename));
            Workbook wb = new XSSFWorkbook(file);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            String[] input;
            int i;
            int indexOfLastCompany = 0;
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cells = currentRow.iterator();
                input = new String[5];
                i = 0;
                while (cells.hasNext()) {
                    Cell currentCell = cells.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        input[i] = cleanString(new DataFormatter().formatCellValue(currentCell));
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        input[i] = cleanString(String.valueOf(new DataFormatter().formatCellValue(currentCell)));
                    }
                    i++;
                }
                OpportunityNote on = new OpportunityNote();
                if (!input[0].equals("")) {
                    on.setCompanyName(input[0]);
                    indexOfLastCompany = notes.size();
                } else {
                    on.setCompanyName(notes.get(indexOfLastCompany).getCompanyName());
                }
                on.setNote(input[4]);
                notes.add(on);
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        return notes;
    }

    public static String cleanString(String input) {
        input = input.replaceAll("\\r", "");
        input = input.replaceAll("\\n", "");
        input = input.replaceAll(",", "\",");
        input = input.trim();
        return input;
    }
}
