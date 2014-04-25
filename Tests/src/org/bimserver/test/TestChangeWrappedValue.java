package org.bimserver.test;

/******************************************************************************
 * Copyright (C) 2009-2014  BIMserver.org
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************************/

import org.bimserver.client.BimServerClient;
import org.bimserver.client.ClientIfcModel;
import org.bimserver.client.json.JsonBimServerClientFactory;
import org.bimserver.emf.IfcModelInterfaceException;
import org.bimserver.models.ifc2x3tc1.IfcLabel;
import org.bimserver.models.ifc2x3tc1.IfcPropertySingleValue;
import org.bimserver.plugins.services.BimServerClientException;
import org.bimserver.shared.ChannelConnectionException;
import org.bimserver.shared.PublicInterfaceNotFoundException;
import org.bimserver.shared.UsernamePasswordAuthenticationInfo;
import org.bimserver.shared.exceptions.ServiceException;

public class TestChangeWrappedValue {
	public static void main(String[] args) {
		new TestChangeWrappedValue().start();
	}

	private void start() {
		JsonBimServerClientFactory factory = new JsonBimServerClientFactory("http://localhost:8080");
		try {
			BimServerClient client = factory.create(new UsernamePasswordAuthenticationInfo("admin@bimserver.org", "admin"));
			long poid = 2686977;
			long roid = 720899;
			ClientIfcModel model = client.getModel(poid, roid, true);
			
			for (IfcPropertySingleValue prop : model.getAll(IfcPropertySingleValue.class)) {
//				IfcValue value = ((IfcPropertySingleValue) prop).getNominalValue();
//				if(value instanceof IfcLabel){
//					System.out.println(prop.getOid() + " is " + ((IfcLabel) value).getWrappedValue() );
//		    	   ((IfcLabel) value).setWrappedValue(((IfcLabel) value).getWrappedValue() + " changed");
//				}
				
				IfcLabel label = model.create(IfcLabel.class);
				label.setWrappedValue("blabla");
				prop.setNominalValue(label);
			}
			
			model.commit("blaat");
		} catch (ServiceException | ChannelConnectionException e) {
			e.printStackTrace();
		} catch (BimServerClientException e) {
			e.printStackTrace();
		} catch (PublicInterfaceNotFoundException e) {
			e.printStackTrace();
		} catch (IfcModelInterfaceException e) {
			e.printStackTrace();
		}
	}
}
