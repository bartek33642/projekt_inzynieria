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

import java.util.List;

public class Solver {
    public Solver(List<Zone> zones, ParkingLotRequirementsDto requirementsDto) throws ContradictionException {
        final int MAX_VAR = 14;
        final int NB_CLAUSES = zones.size()+9;
        WeightedMaxSatDecorator maxSatSolver = new
                WeightedMaxSatDecorator(SolverFactory.newDefault());
        ModelIterator solver = new ModelIterator(
                new OptToSatAdapter(new PseudoOptDecorator(maxSatSolver)));
        solver.newVar(MAX_VAR);
        solver.setExpectedNumberOfClauses(NB_CLAUSES);
        if (requirementsDto.haveSpaceForHandicapped = true) {
            maxSatSolver.addSoftClause(35, new VecInt(new int[]{8}));
            maxSatSolver.addSoftClause(30, new VecInt(new int[]{13}));
        } else {
            maxSatSolver.addSoftClause(30, new VecInt(new int[]{-8}));
            maxSatSolver.addSoftClause(20, new VecInt(new int[]{-11,9}));
        }
        if (requirementsDto.isPrivate = true) {
            maxSatSolver.addSoftClause(30, new VecInt(new int[]{9}));
            maxSatSolver.addSoftClause(25, new VecInt(new int[]{11}));
            maxSatSolver.addSoftClause(10, new VecInt(new int[]{10}));
        } else {
            maxSatSolver.addSoftClause(25, new VecInt(new int[]{-9}));
            maxSatSolver.addSoftClause(20, new VecInt(new int[]{-13,-11}));
        }
    }
}
