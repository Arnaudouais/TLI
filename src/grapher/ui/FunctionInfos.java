package grapher.ui;

import grapher.fc.*;

public class FunctionInfos {
	Function f;
	Boolean selected;
	//Color ?
	public FunctionInfos(Function f, Boolean selected){
		this.f = f;
		this.selected = selected;
	}
	
	public void setSelected(Boolean b){
		selected = b;
	}
	
	@Override
	public String toString(){
		return f.toString();
	}
}
