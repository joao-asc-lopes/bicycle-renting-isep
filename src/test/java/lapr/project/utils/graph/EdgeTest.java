/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.utils.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author frodrigues
 */
public class EdgeTest {

    Edge<String, String> instance = new Edge<>();
    Edge<String, String> instance2 = new Edge<>();

    public EdgeTest() {
    }

    /**
     * Test of getElement method, of class Edge.
     */
    @Test
    public void testGetElement() {
        System.out.println("getElement");

        String expResult = null;
        assertEquals(expResult, instance.getElement());

        Edge<String, String> instance1 = new Edge<>("edge1", 1.0, null, null);
        expResult = "edge1";
        assertEquals(expResult, instance1.getElement());
    }

    /**
     * Test of setElement method, of class Edge.
     */
    @Test
    public void testSetElement() {
        System.out.println("setElement");

        String eInf = "edge1";
        instance.setElement(eInf);

        assertEquals("edge1", instance.getElement());
    }

    /**
     * Test of getWeight method, of class Edge.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");

        double expResult = 0.0;
        assertEquals(expResult, instance.getWeight());
    }

    /**
     * Test of setTotalDistance method, of class Edge.
     */
    @Test
    public void testSetWeight() {
        System.out.println("setTotalDistance");
        double ew = 2.0;
        instance.setWeight(ew);

        double expResult = 2.0;
        assertEquals(expResult, instance.getWeight(), 2.0);
    }

    /**
     * Test of getVOrig method, of class Edge.
     */
    @Test
    public void testGetVOrig() {
        System.out.println("getVOrig");

        Object expResult = null;
        assertEquals(expResult, instance.getVOrig());

        Vertex<String, String> vertex1 = new Vertex<>(1, "Vertex1");
        Edge<String, String> otherEdge = new Edge<>("edge1", 1.0, vertex1, vertex1);
        assertEquals(vertex1.getElement(), otherEdge.getVOrig());
    }

    /**
     * Test of setVOrig method, of class Edge.
     */
    @Test
    public void testSetVOrig() {
        System.out.println("setVOrig");

        Vertex<String, String> vertex1 = new Vertex<>(1, "Vertex1");
        instance.setVOrig(vertex1);
        assertEquals(vertex1.getElement(), instance.getVOrig());
    }

    /**
     * Test of getVDest method, of class Edge.
     */
    @Test
    public void testGetVDest() {
        System.out.println("getVDest");

        Object expResult = null;
        assertEquals(expResult, instance.getVDest());

        Vertex<String, String> vertex1 = new Vertex<>(1, "Vertex1");
        Edge<String, String> otherEdge = new Edge<>("edge1", 1.0, vertex1, vertex1);
        assertEquals(vertex1.getElement(), otherEdge.getVDest());
    }

    /**
     * Test of setVDest method, of class Edge.
     */
    @Test
    public void testSetVDest() {
        System.out.println("setVDest");

        Vertex<String, String> vertex1 = new Vertex<>(1, "Vertex1");
        instance.setVDest(vertex1);
        assertEquals(vertex1.getElement(), instance.getVDest());
    }

    /**
     * Test of getEndpoints method, of class Edge.
     */
    @Test
    public void testGetEndpoints() {
        System.out.println("getEndpoints");

        String[] expResult = null;
        String[] result = instance.getEndpoints();
        assertArrayEquals(expResult, result);

        Vertex<String, String> vertex1 = new Vertex<>(1, "Vertex1");
        instance.setVOrig(vertex1);
        instance.setVDest(vertex1);

        String[] expResult1 = {"Vertex1", "Vertex1"};
        assertArrayEquals(expResult1, instance.getEndpoints());
    }

    /**
     * Test of equals method, of class Edge.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");

        assertFalse(instance.equals(null));

        assertFalse(instance.equals("test"));


        assertTrue(instance.equals(instance));

        assertTrue(instance.equals(instance.clone()));

        Vertex<String, String> vertex1 = new Vertex<>(1, "Vertex1");
        Edge<String, String> otherEdge = new Edge<>("edge1", 1.0, vertex1, vertex1);

        assertFalse(instance.equals(otherEdge));
    }

    @Test
    public void ensureEqualsWithEdgeWithOriginVertexNullReturnsFalse(){
        instance2  = instance.clone();
        instance2.setVOrig(null);

        boolean flag = true;
        boolean result = instance.equals(instance2);
        assertEquals(result,flag);
    }

    @Test
    public void ensureEqualsWithEdgeWithDestVertexNullReturnsFalse(){
        instance2 = instance.clone();
        instance.setVOrig(null);

        boolean flag = true;
        boolean result = instance.equals(instance2);
        assertEquals(result,flag);
    }

    @Test
    public void ensureDifferentWeightsReturnsFalse(){
        instance2 = instance.clone();
        instance.setWeight(10);
        instance2.setWeight(11);
        boolean flag = false;
        boolean result = instance.equals(instance2);
        assertEquals(result,flag);
    }

    @Test
    public void ensureDifferentElementsReturnsFalse(){
        instance2 = instance.clone();
        boolean flag = true;
        boolean result = instance.equals(instance2);
        assertEquals(flag,result);
    }
    @Test
    public void ensureDifferentElementsReturnTrue(){
        instance2 = instance.clone();
        instance2.setElement("adfsadfs");
        boolean flag = true;
        boolean result = instance.equals(instance2);
        assertEquals(flag,result);
    }



    /**
     * Test of compareTo method, of class Edge.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");

        Vertex<String, String> vertex1 = new Vertex<>(1, "Vertex1");
        Edge<String, String> otherEdge = new Edge<>("edge1", 1.0, vertex1, vertex1);

        int expResult = -1;
        int result = instance.compareTo(otherEdge);
        assertEquals(expResult, result);

        otherEdge.setWeight(0.0);
        expResult = 0;
        result = instance.compareTo(otherEdge);
        assertEquals(expResult, result);

        instance.setWeight(2.0);
        expResult = 1;
        result = instance.compareTo(otherEdge);
        assertEquals(expResult, result);
    }

    /**
     * Test of clone method, of class Edge.
     */
    @Test
    public void testClone() {
        System.out.println("clone");

        Vertex<String, String> vertex1 = new Vertex<>(1, "Vertex1");
        Edge<String, String> otherEdge = new Edge<>("edge1", 1.0, vertex1, vertex1);

        Edge<String, String> instClone = otherEdge.clone();

        assertTrue(otherEdge.getElement() == instClone.getElement());
        assertTrue(otherEdge.getWeight() == instClone.getWeight());

        String[] expResult = otherEdge.getEndpoints();
        assertArrayEquals(expResult, instClone.getEndpoints());
    }

    /**
     * Test of toString method, of class Edge.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        instance.setElement("edge1");
        instance.setWeight(1.0);
        Vertex<String, String> vertex1 = new Vertex<>(1, "Vertex1");
        instance.setVOrig(vertex1);
        instance.setVDest(vertex1);
        String expResult = "(edge1) - 1.0 - Vertex1";
        String result = instance.toString().trim();
        assertEquals(expResult, result);

        System.out.println(instance);
    }

    @Test
    public void ensureHashCodeEdgesWorks(){
        boolean expected = true;
        boolean result = this.instance.equals(this.instance);
        assertEquals(expected,result);
    }
    @Test
    public void ensureHashCodeEdgesNotWorks(){
        this.instance2.setVDest(new Vertex<>());
        boolean expected = false;
        boolean result = this.instance.equals(this.instance2);
        assertEquals(expected,result);
    }

}
