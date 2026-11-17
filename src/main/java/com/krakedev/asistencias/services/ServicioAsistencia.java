package com.krakedev.asistencias.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.krakedev.asistencias.entidades.Asistencia;
import com.krakedev.asistencias.entidades.Estudiante;
import com.krakedev.asistencias.entidades.RegistroAsistencia;

@Service
public class ServicioAsistencia {
	
	private ArrayList<RegistroAsistencia> registros = new ArrayList<>();
	private final ServicioEstudiantes servicioEstudiantes;
	
	// Constructor para inyectar la dependencia de ServicioEstudiantes
	public ServicioAsistencia(ServicioEstudiantes servicioEstudiantes) {
		this.servicioEstudiantes = servicioEstudiantes;
	}
	
	public RegistroAsistencia registrarAsistencia(String cedula) {
		// 1. Buscar estudiante por cedula en servicioEstudiantes
		Estudiante estudiante = servicioEstudiantes.buscarPorCedula(cedula);
		
		// 2. Si no existe el estudiante retorna null
		if (estudiante == null) {
			return null;
		}
		
		// 3. Crea la asistencia con la fecha y hora actuales
		Asistencia asistencia = new Asistencia(
				LocalDate.now(),
				LocalDateTime.now(),
				"P"
		);
		
		// 4. Crea un registro asistencia, agrega a la lista y retorna el registro creado
		RegistroAsistencia registro = new RegistroAsistencia(estudiante, asistencia);
		registros.add(registro);
		return registro;
	}
	
	public ArrayList<Asistencia> consultarAsistencia(String cedula) {
		// Retorna todas las asistencias del estudiante
		ArrayList<Asistencia> resultado = new ArrayList<>();
		for (RegistroAsistencia reg : registros) {
			if (reg.getEstudiante().getCedula().equals(cedula)) {
				resultado.add(reg.getAsistencia());
			}
		}
		return resultado;
	}
}