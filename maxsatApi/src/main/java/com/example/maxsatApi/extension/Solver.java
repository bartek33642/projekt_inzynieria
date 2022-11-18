package com.example.maxsatApi.extension;

import com.example.maxsatApi.dto.ParkingLotRequirementsDto;
import com.example.maxsatApi.dto.ParkingLotWithScore;
import com.example.maxsatApi.model.ParkingLot;
import com.example.maxsatApi.model.Zone;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import org.sat4j.core.VecInt;
import org.sat4j.maxsat.SolverFactory;
import org.sat4j.maxsat.WeightedMaxSatDecorator;
import org.sat4j.pb.PseudoOptDecorator;
import org.sat4j.specs.ContradictionException;
import org.sat4j.tools.ModelIterator;
import org.sat4j.tools.OptToSatAdapter;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

public class Solver {
    private List<Integer> result;
    private List<Zone> selectedZones;
    List<Integer> selectedZonesIds;
    Zone mostNeededZone;
    List<Integer> preferredParkingLotIds;
    public Solver(List<Zone> allZones, ParkingLotRequirementsDto requirementsDto, List<ParkingLot> preferredParkingLots) throws Exception {
        this.preferredParkingLotIds = preferredParkingLots.stream().map(x -> x.getParkingLotId()).toList();
        this.selectedZones = findSelectedZones(allZones, requirementsDto);
        this.mostNeededZone = findMostNeededZone(selectedZones);
        this.selectedZonesIds = selectedZones.stream().map(x->x.getZoneId()).toList();
        this.result = findResult(requirementsDto);
    }

    private List<Zone> findSelectedZones(List<Zone> allZones, ParkingLotRequirementsDto requirementsDto){
        int requiredParkingLotZoneCordX = requirementsDto.getZoneCordX();
        int requiredParkingLotZoneCordY = requirementsDto.getZoneCordY();
        List<Zone> selectedZones = allZones.stream().filter( z -> isNeighbourHexagon(
                z, requiredParkingLotZoneCordX, requiredParkingLotZoneCordY
        )).toList();
        return selectedZones;
    }
    private boolean isNeighbourHexagon(Zone zone, int cordX, int cordY){
        int cordYDifference = zone.getCordY() - cordY;
        int cordXDifference = zone.getCordX() - cordX;
        boolean isNeighbourOnYAxis = cordXDifference == 0
                && Math.abs(zone.getCordY() - cordY) <= 1;
        if (isNeighbourOnYAxis) return true;
        return
                Math.abs(cordXDifference) == 1
                        && (cordYDifference == 0 ||
                        (cordX % 2 == 1 ?
                            cordYDifference == 1
                            :
                            cordYDifference == -1));
    }
    private List<Integer> findResult(ParkingLotRequirementsDto requirementsDto) throws ContradictionException {
        final int SELECTED_ZONES_SIZE = selectedZones.size();
        final int MAX_VAR = 14;
        final int NUMBER_OF_CLAUSES = SELECTED_ZONES_SIZE+9;
        WeightedMaxSatDecorator maxSatSolver = new
                WeightedMaxSatDecorator(SolverFactory.newDefault());
        ModelIterator solver = new ModelIterator(
                new OptToSatAdapter(new PseudoOptDecorator(maxSatSolver)));
        solver.newVar(MAX_VAR);
        solver.setExpectedNumberOfClauses(NUMBER_OF_CLAUSES);
        for (int literal = 1; literal < SELECTED_ZONES_SIZE+1; literal++){
            int indexOfZone = literal - 1;
            int zoneId = selectedZonesIds.get(indexOfZone);
            if (zoneId == mostNeededZone.getZoneId())
                maxSatSolver.addSoftClause(new VecInt(new int[]{literal}));
            else
                maxSatSolver.addSoftClause(new VecInt(new int[]{0-literal}));
        }
        if (requirementsDto.isHaveSpaceForHandicapped() == true) {
            maxSatSolver.addSoftClause(35, new VecInt(new int[]{8}));
            maxSatSolver.addSoftClause(30, new VecInt(new int[]{13}));
        } else {
            maxSatSolver.addSoftClause(30, new VecInt(new int[]{-8}));
            maxSatSolver.addSoftClause(20, new VecInt(new int[]{-11,9}));
        }
        if (requirementsDto.isPrivate() == true) {
            maxSatSolver.addSoftClause(30, new VecInt(new int[]{9}));
            maxSatSolver.addSoftClause(25, new VecInt(new int[]{11}));
            maxSatSolver.addSoftClause(10, new VecInt(new int[]{10}));
        } else {
            maxSatSolver.addSoftClause(25, new VecInt(new int[]{-9}));
            maxSatSolver.addSoftClause(20, new VecInt(new int[]{-13,-11}));
        }
        if (requirementsDto.isPaid() == true) {
            maxSatSolver.addSoftClause(20, new VecInt(new int[]{10}));
            maxSatSolver.addSoftClause(15, new VecInt(new int[]{11}));
            maxSatSolver.addSoftClause(10, new VecInt(new int[]{9}));
        } else {
            maxSatSolver.addSoftClause(30, new VecInt(new int[]{-10}));
            maxSatSolver.addSoftClause(15, new VecInt(new int[]{-13, -11}));
        }
        if (requirementsDto.isGuarded() == true) {
            maxSatSolver.addSoftClause(30, new VecInt(new int[]{11}));
            maxSatSolver.addSoftClause(25, new VecInt(new int[]{9}));
            maxSatSolver.addSoftClause(10, new VecInt(new int[]{10}));
        } else {
            maxSatSolver.addSoftClause(20, new VecInt(new int[]{-11}));
            maxSatSolver.addSoftClause(15, new VecInt(new int[]{-13, -9}));
        }
        if (requirementsDto.isHaveSpacesForElectrics() == true) {
            maxSatSolver.addSoftClause(30, new VecInt(new int[]{12}));
            maxSatSolver.addSoftClause(25, new VecInt(new int[]{13}));
        } else {
            maxSatSolver.addSoftClause(20, new VecInt(new int[]{-12}));
            maxSatSolver.addSoftClause(15, new VecInt(new int[]{9, 10, -13}));
        }
        if (requirementsDto.isAtLeast15FreePlaces() == true) {
            maxSatSolver.addSoftClause(15, new VecInt(new int[]{13}));
            maxSatSolver.addSoftClause(10, new VecInt(new int[]{10, 11, 9}));
        } else {
            maxSatSolver.addSoftClause(30, new VecInt(new int[]{-13}));
        }
        maxSatSolver.addSoftClause(25, new VecInt(new int[]{14}));
        try {
            if (solver.isSatisfiable()){
                int [] model = solver.model();
                return Arrays.stream(model).boxed().toList();

            }else{
                throw new Exception("Unsatisfiable formula");
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    private Zone findMostNeededZone(List<Zone> zones){
        double maxWeight = 0;
        Zone mostNeededZone = new Zone();
        for (Zone zone : zones) {
            double weight = calculateWeightOfZone(zone);
            if (weight > maxWeight) {
                mostNeededZone = zone;
                maxWeight = weight;
            }
        }
        return mostNeededZone;
    }

    private static long calculateWeightOfZone(Zone zone) {
        return Math.round((zone.getDemandFactor() + zone.getAccessibilityFactor() + zone.getAttractivenessFactor()) / 0.03);
    }

    public List<ParkingLotWithScore> findTheBestParkingLotsWithScores(){
        List<ParkingLot> selectedParkingLots = new ArrayList<>();
        selectedZones.forEach(zone -> selectedParkingLots.addAll(zone.getParkingLots()));
        List<ParkingLotWithScore> parkingLotsWithScores = new ArrayList<>();
        selectedParkingLots.forEach(parkingLot ->
                parkingLotsWithScores.add(new ParkingLotWithScore(
                        parkingLot, test(parkingLot)
                )));
        parkingLotsWithScores.sort(Comparator.comparingInt(ParkingLotWithScore::getScore).reversed());
        return parkingLotsWithScores.subList(0,3);
    }
    public int test(ParkingLot parkingLot) {
        int score = 0;
        int currentParkingLotZoneId = parkingLot.getZoneId();
        int indexOfCurrentParkingLotZoneId = selectedZonesIds.indexOf(currentParkingLotZoneId);
        if (indexOfCurrentParkingLotZoneId >(-1) && result.get(indexOfCurrentParkingLotZoneId)>0)
            score+=10;
        if (result.contains(8) && parkingLot.getHaveSpaceForHandicapped())
            score++;
        else if (result.contains(-8) && !parkingLot.getHaveSpaceForHandicapped())
            score++;
        if (result.contains(9) && parkingLot.isPrivate())
            score++;
        else if (result.contains(-9) && !parkingLot.isPrivate())
            score++;
        if (result.contains(10) && parkingLot.isPaid())
            score++;
        else if (result.contains(-10) && !parkingLot.isPaid())
            score++;
        if (result.contains(11) && parkingLot.isGuarded())
            score++;
        else if (result.contains(-11) && !parkingLot.isGuarded())
            score++;
        if (result.contains(12) && parkingLot.isHaveSpacesForElectrics())
            score++;
        else if (result.contains(-12) && !parkingLot.isHaveSpacesForElectrics())
            score++;
        if (result.contains(13) && parkingLot.getFreeSpaces() > 14)
            score++;
        else if (result.contains(-13) && parkingLot.getFreeSpaces() < 15)
            score++;
        if (result.contains(14) &&  preferredParkingLotIds.contains(parkingLot.getParkingLotId()))
            score++;
        return score;
    }
}
