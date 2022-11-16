package com.example.maxsatApi.extension;

import com.example.maxsatApi.dto.ParkingLotRequirementsDto;
import com.example.maxsatApi.model.ParkingLot;
import com.example.maxsatApi.model.Zone;
import org.sat4j.core.VecInt;
import org.sat4j.maxsat.SolverFactory;
import org.sat4j.maxsat.WeightedMaxSatDecorator;
import org.sat4j.pb.PseudoOptDecorator;
import org.sat4j.specs.ContradictionException;
import org.sat4j.tools.ModelIterator;
import org.sat4j.tools.OptToSatAdapter;

import java.util.*;

public class Solver {
    private List<Integer> result;
    private List<Zone> selectedZones;
    List<Integer> selectedZonesIds;
    Zone zoneWithRequiredCords;
    List<Integer> preferredParkingLotIds;
    public Solver(List<Zone> allZones, ParkingLotRequirementsDto requirementsDto, List<ParkingLot> preferredParkingLots) throws Exception {
        this.preferredParkingLotIds = preferredParkingLots.stream().map(x -> x.getParkingLotId()).toList();
        setSelectedZones(allZones, requirementsDto);
        this.selectedZonesIds = selectedZones.stream().map(x->x.getZoneId()).toList();
        setResult(requirementsDto);
    }

    private void setSelectedZones(List<Zone> allZones, ParkingLotRequirementsDto requirementsDto) throws Exception {
        int requiredParkingLotZoneCordX = requirementsDto.getZoneCordX();
        int requiredParkingLotZoneCordY = requirementsDto.getZoneCordY();
        List<Zone> selectedZones = allZones.stream().filter( z -> isNeighbourHexagon(
                z, requiredParkingLotZoneCordX, requiredParkingLotZoneCordY
        )).toList();
        zoneWithRequiredCords = selectedZones.stream().filter(
                x -> x.getCordX() == requiredParkingLotZoneCordX &&
                        x.getCordY() == requiredParkingLotZoneCordY
        ).findFirst().orElse(null);
        this.selectedZones = selectedZones;
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
//        boolean haveCloseXCoordinates = (
//                zone.getCordX() >= cordX - 1 &&
//                zone.getCordX() <= cordX + 1);
//        if (!haveCloseXCoordinates)
//            return false;
//        return cordX % 2 == 0 ?
//                zone.getCordY() >= cordY - 1 &&
//                        (zone.getCordY() <= cordY || zone.getCordX() == cordX && zone.getCordY() ==)
//                :
//                zone.getCordY() <= cordY + 1 &&
//                        (zone.getCordY() >= cordY || zone.getCordX() == cordX);
    }
    private void setResult(ParkingLotRequirementsDto requirementsDto) throws ContradictionException {
        final int MAX_VAR = 14;
        final int NUMBER_OF_CLAUSES = selectedZones.size()+9;
        WeightedMaxSatDecorator maxSatSolver = new
                WeightedMaxSatDecorator(SolverFactory.newDefault());
        ModelIterator solver = new ModelIterator(
                new OptToSatAdapter(new PseudoOptDecorator(maxSatSolver)));
        solver.newVar(MAX_VAR);
        solver.setExpectedNumberOfClauses(NUMBER_OF_CLAUSES);
        for (int literal = 1; literal < 8; literal++){
            int indexOfZone = literal - 1;
            //TODO: rozwiązać problem index out of bound przy liczbie sąsiadów mniejszej od 7
            int zoneId = selectedZonesIds.get(indexOfZone);
            if (zoneId == zoneWithRequiredCords.getZoneId())
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
                result = Arrays.stream(model).boxed().toList();

            }else{
                throw new Exception("Unsatisfiable formula");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public int test(ParkingLot parkingLot) {
        int score = 0;
        int selectedZoneId = parkingLot.getZoneId();
        int indexOfSelectedZoneId = selectedZonesIds.indexOf(selectedZoneId);
        if (indexOfSelectedZoneId >(-1) && result.get(indexOfSelectedZoneId)>0)
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
