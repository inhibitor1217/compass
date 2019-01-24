#version 400 core

in vec2 position;
in vec2 uv;

out vec2 pass_uv;

uniform mat4 transformation;
uniform mat4 projection;

void main(void) {
	
	gl_Position = projection * transformation * vec4(position, 0.0, 1.0);
	pass_uv = uv;
	
}