package fr.mla.csp.problem;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import fr.mla.NoSolutionException;
import fr.mla.csp.CSP;
import fr.mla.csp.Variable;
import lombok.extern.slf4j.Slf4j;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
public class Squadra extends CSP<SquadraVariable, SquadraValue> {

  public static final int PILOT_NUMBER = 6;
  private static final String LEGS_FILE = "legs.tsv";

  public Squadra() {
    List<String[]> legs;
    try {
      legs = readLegsFile();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    Set<SquadraVariable> variables = new HashSet<>();
    for (String[] leg : legs) {
      for (SquadraVariable.Role role : SquadraVariable.Role.values()) {
        variables.add(new SquadraVariable(new SquadraVariable.Leg(leg), role));
      }
    }

    this.variables = variables;
  }

  @Override
  protected boolean isConsistent(SquadraVariable variable, SquadraValue value, Map<SquadraVariable, SquadraValue> assignment) {
    if (!isXXXX1(variable, value, assignment)) {
      return false;
    }
    if (!isXXXX2(variable, value, assignment)) {
      return false;
    }
    if (!isXXXX3(variable, value, assignment)) {
      return false;
    }
    if (!isXXXX4(variable, value, assignment)) {
      return false;
    }
    return true;
  }

  private boolean isXXXX1(SquadraVariable variable, SquadraValue value, Map<SquadraVariable, SquadraValue> assignment) {
    return true;
  }

  private boolean isXXXX2(SquadraVariable variable, SquadraValue value, Map<SquadraVariable, SquadraValue> assignment) {
    return true;
  }

  private boolean isXXXX3(SquadraVariable variable, SquadraValue value, Map<SquadraVariable, SquadraValue> assignment) {
    return true;
  }

  private boolean isXXXX4(SquadraVariable variable, SquadraValue value, Map<SquadraVariable, SquadraValue> assignment) {
    return true;
  }



  private CSVReader getCsvReader(Reader reader) {
    CSVParser parser = new CSVParserBuilder()
            .withSeparator('\t')
            .withIgnoreQuotations(true)
            .build();

    return new CSVReaderBuilder(reader)
            .withSkipLines(0)
            .withCSVParser(parser)
            .build();
  }


  private List<String[]> readLegsFile() throws Exception {
    Path path = Paths.get(ClassLoader.getSystemResource(LEGS_FILE).toURI());
    try (Reader reader = Files.newBufferedReader(path)) {
      try (CSVReader csvReader = getCsvReader(reader)) {
        return csvReader.readAll();
      }
    }
  }


  public static void main(String[] args) {
    Squadra squadra = new Squadra();
    try {
      Map<SquadraVariable, SquadraValue> assignment = squadra.backtrackingSearch();
      for (SquadraVariable v : assignment.keySet().stream().sorted(Comparator.comparing(Variable::get)).toList()) {
        log.info("{} : {}", v, assignment.get(v));
      }
    } catch (NoSolutionException e) {
      log.error("No Solution Found");
    }
  }

}
