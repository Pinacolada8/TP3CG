package raytracer.scene;

import raytracer.Ray;
import raytracer.RayResponse;
import raytracer.math.Constants;
import raytracer.math.Vector3;
import java.lang.Math;

public class Object {

    public Pygment pygment;
    public Material material;
    public String type;
    public Vector3 position;
    public double radius;

    /**
     * *
     * Retorna um objeto do tipo RayResponse com informações sobre uma possível
     * interseção do raio com este objeto (veja o arquivo ray.h com a declaração
     * da struct RayResponse).
     *
     * O objeto response deve preencher os seguintes valores:
     *  - response.intersected, true/false indicando se houve interseção ou não
     *  - response.intersectionT, o valor T do raio (semi-reta) da primeira 
     *    interseção, caso haja mais que 1 interseção
     *  - response.intersectionPoint, contendo o ponto (x,y,z) de interseção
     *  - ray.intersectionNormal, contendo o vetor normal do objeto no
     *    ponto de interseção. A normal deve ser *normalizada* (norma = 1)
     * 
     *
     * @param ray
     * @return
     */
        double TINY = 1E-3;
    public RayResponse intersectsWith(Ray ray) {
        double delta;
        double intersectionT1;
        double intersectionT2;
        // Inicia uma resposta com um "intersects" falso,
        // indicando que não houve interseção
        RayResponse response = new RayResponse();        
        response.intersected = false;
        
        response.intersectionPoint = new Vector3();
        response.intersectionNormal = new Vector3();

        // Está sempre retornando false, por isso a imagem gerada não tem nada
        // Você deve implementar este método de forma que ele retorne true/false
        // dependendo se o raio acerta ou não a esfera
        
        delta = Math.pow(fb(ray.p0,ray.v),2) - 4*(fa(ray.v))*(fc(ray.p0));
        
        if (delta == 0){
            response.intersected = true;
            response.intersectionT = (- fb(ray.p0, ray.v))/(2*fa(ray.v));
            
            response.intersectionPoint.x =ray.p0.x + (response.intersectionT*ray.v.x);
            response.intersectionPoint.y =ray.p0.y + (response.intersectionT*ray.v.y);
            response.intersectionPoint.z =ray.p0.z + (response.intersectionT*ray.v.z);
        }else if (delta > TINY){
            response.intersected = true;
            
            intersectionT1 = ((- fb(ray.p0, ray.v)) + Math.pow(delta, 0.5))/(2*fa(ray.v));
            intersectionT2 = ((- fb(ray.p0, ray.v)) - Math.pow(delta, 0.5))/(2*fa(ray.v));
            
            if ((intersectionT1 < intersectionT2) && (intersectionT1 >= 0)){
                response.intersectionT = intersectionT1;
            }else if (intersectionT2 >= 0){
                response.intersectionT = intersectionT2;
            }else if (intersectionT1 >= 0){
                response.intersectionT = intersectionT1;
            }else{
                response.intersectionT = 0;
            }           

            response.intersectionPoint.x =ray.p0.x + (response.intersectionT*ray.v.x);
            response.intersectionPoint.y =ray.p0.y + (response.intersectionT*ray.v.y);
            response.intersectionPoint.z =ray.p0.z + (response.intersectionT*ray.v.z);
        }
        
        
        if (delta >= TINY){
            response.intersectionNormal.x = response.intersectionPoint.x - position.x;
            response.intersectionNormal.y = response.intersectionPoint.y - position.y;
            response.intersectionNormal.z = response.intersectionPoint.z - position.z;
        }
        
        
        
        return response;
    }
    
    public double fb(Vector3 p, Vector3 u){
        return (2)*(u.x*(position.x -p.x) + u.y*(position.y -p.y) + u.z*(position.z -p.z));
    }
    
    public double fa(Vector3 u){
        //return (Math.pow(u.x, 2)+ Math.pow(u.y, 2)+ Math.pow(u.z, 2));
        return 1;
    }
    
    public double fc(Vector3 p){
        return (Math.pow((position.x -p.x), 2) + Math.pow((position.y -p.y), 2)+Math.pow((position.z -p.z), 2)) - Math.pow(radius, 2);
    }
    
    

}
