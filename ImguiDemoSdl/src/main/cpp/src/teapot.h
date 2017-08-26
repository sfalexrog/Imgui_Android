//
// Created by sf on 8/9/17.
//

#ifndef IMGUI_DEMO_TEAPOT_H
#define IMGUI_DEMO_TEAPOT_H

#ifdef GL_PROFILE_GL3
#include "gl_glcore_3_3.h"
#else
#include <GLES2/gl2.h>
#endif

#include <glm/common.hpp>
#include <glm/matrix.hpp>

class Teapot {
public:
    Teapot();
    ~Teapot();
    bool init();
    void draw();
    void rotateBy(float angleX, float angleY);
    void rotateTo(float angleX);
    void rotateCameraBy(float angleX, float angleY);
    void rotateCameraTo(float angleX);
    void zoomBy(float zoomFactor);
    float zoomValue();

private:
    struct vtxData {
        glm::vec3 pos;
        glm::vec3 norm;
        glm::vec3 tangent;
        glm::vec3 binorm;
        glm::vec3 texcoord;
    };

    struct uniformData {
        glm::mat4 world;
        glm::mat4 worldInverseTranspose;
        glm::mat4 worldViewProj;
        glm::mat4 viewInverse;
    } unfData;

#ifdef GL_PROFILE_GL3
    static GLuint g_vao;
#endif
    int num_vertices;
    int num_indices;
    GLuint ibo;
    GLuint vbo;
    GLuint tex_skybox;
    GLuint tex_bump;

    struct {
        GLint g_Position;
        GLint g_TexCoord0;
        GLint g_Tangent;
        GLint g_Binormal;
        GLint g_Normal;
    } attribs;

    struct {
        GLint world;
        GLint worldInverseTranspose;
        GLint worldViewProj;
        GLint viewInverse;
        GLint normalSampler;
        GLint envSampler;
    } uniforms;
    GLuint program;

    GLfloat rotX, rotY, zoom;
    GLfloat camRX, camRY;
    GLfloat addRotX, addRotY, addZoom;
    GLfloat addCamRX, addCamRY;

    bool compileShaders();

};

#endif //IMGUI_DEMO_TEAPOT_H
