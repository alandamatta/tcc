package br.edu.dmsoftware.tcc.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.dmsoftware.tcc.modelo.Entrada;

@FacesConverter("enumEntradaConverter")
public class EnumEntradaConverter implements Converter{
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null) {
			return Entrada.valueOf(value);
		}
		return null;
	}
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && value instanceof Entrada) {
			return ((Entrada) value).name();
		}
		return null;
	}
}
