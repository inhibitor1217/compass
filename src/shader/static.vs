#version 400 core

in vec2 position;
in vec2 uv;

out vec2 pass_uv;

uniform mat4 transformation;
uniform mat4 projection;
uniform vec4 boundBox;
uniform float mirror;

void main(void) {
	
	gl_Position = projection * transformation * vec4(position, 0.0, 1.0);
	if (mirror == 1.0f)
		pass_uv = vec2(boundBox.x + boundBox.z - uv.x, uv.y);
	else
		pass_uv = uv;
	
}