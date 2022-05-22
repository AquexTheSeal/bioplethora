package io.github.bioplethora.api.extras;

import net.minecraft.util.math.vector.Vector3d;

public class MathUtils {

    public static Vector3d transform(Vector3d axis, double angle, Vector3d normal) {

        double m00 = 1, m01 = 0, m02 = 0;
        double m10 = 0, m11 = 1, m12 = 0;
        double m20 = 0,  m21 = 0,  m22 = 1;

        double mag = Math.sqrt(axis.x * axis.x + axis.y * axis.y + axis.z * axis.z);
        if (mag >= 1.0E-10) {
            mag = 1.0 / mag;
            double ax = axis.x * mag, ay = axis.y * mag, az = axis.z * mag;

            double sinTheta = Math.sin(angle), cosTheta = Math.cos(angle);
            double t = 1.0 - cosTheta;

            double xz = ax * az, xy = ax * ay, yz = ay * az;

            m00 = t * ax * ax + cosTheta;
            m01 = t * xy - sinTheta * az;
            m02 = t * xz + sinTheta * ay;

            m10 = t * xy + sinTheta * az;
            m11 = t * ay * ay + cosTheta;
            m12 = t * yz - sinTheta * ax;

            m20 = t * xz - sinTheta * ay;
            m21 = t * yz + sinTheta * ax;
            m22 = t * az * az + cosTheta;
        }
        return new Vector3d(m00 * normal.x + m01 * normal.y + m02 * normal.z, m10 * normal.x + m11 * normal.y + m12 * normal.z, m20 * normal.x + m21 * normal.y + m22 * normal.z);
    }

    public static double angleBetween(Vector3d v1, Vector3d v2) {
        double vDot = v1.dot(v2) / (v1.length() * v2.length());
        if (vDot < -1.0) {
            vDot = -1.0;
        }
        if (vDot > 1.0) {
            vDot = 1.0;
        }
        return Math.acos(vDot);
    }

    public static double wrap180Radian(double radian) {
        radian %= 2 * Math.PI;
        while (radian >= Math.PI) {
            radian -= 2 * Math.PI;
        }
        while (radian < -Math.PI) {
            radian += 2 * Math.PI;
        }
        return radian;
    }

    public static double clampAbs(double param, double maxMagnitude) {
        if (Math.abs(param) > maxMagnitude) {

            if (param < 0) {
                param = -Math.abs(maxMagnitude);
            } else {
                param = Math.abs(maxMagnitude);
            }
        }
        return param;
    }
}
