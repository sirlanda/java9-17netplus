package hu.tyufi.varhandle;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class VarHandleShowcase {

    public int egyszam = 5;
    private int ketszam = 11;

    private static final VarHandle EGYSZAM_HANDLE;
    private static final VarHandle KETSZAM_HANDLE;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            EGYSZAM_HANDLE = lookup.findVarHandle(VarHandleShowcase.class, "egyszam", int.class);
            KETSZAM_HANDLE = MethodHandles.privateLookupIn(VarHandleShowcase.class, lookup)
                    .findVarHandle(VarHandleShowcase.class, "ketszam", int.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        VarHandleShowcase handleShowcase = new VarHandleShowcase();

        assert EGYSZAM_HANDLE.coordinateTypes().size() == 1;
        assert EGYSZAM_HANDLE.coordinateTypes().get(0).equals(VarHandleShowcase.class);
        assert 5 == (int) EGYSZAM_HANDLE.get(handleShowcase);
        EGYSZAM_HANDLE.set(handleShowcase, 6);
        assert 6 == (int) EGYSZAM_HANDLE.get(handleShowcase);
        assert 6 == handleShowcase.egyszam;
        assert KETSZAM_HANDLE.coordinateTypes().size() == 1;
        assert KETSZAM_HANDLE.coordinateTypes().get(0).equals(VarHandleShowcase.class);
        assert 11 == (int) KETSZAM_HANDLE.get(handleShowcase);
        KETSZAM_HANDLE.set(handleShowcase, 23);
        assert 23 == (int) KETSZAM_HANDLE.get(handleShowcase);
        assert 23 == handleShowcase.ketszam;

        // atomi módosítások
        EGYSZAM_HANDLE.getAndAdd(handleShowcase, 1);
        assert 7 == handleShowcase.egyszam;
        EGYSZAM_HANDLE.compareAndSet(handleShowcase, 7, 8);
        assert 8 == handleShowcase.egyszam;

    }


}
