package com.automation.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;

public class GenerateReport {
	private PropertySettings ps;
	
	public GenerateReport(PropertySettings pSettings){
		this.ps = pSettings;
	}
	
	public void designExtendReport(){
		
		String html = "<div class=\"col s12 m6 l6 np-h\"><div class=\"card-panel nm-v\"> <span class=\"panel-name\">Pass Percentage</span><span class=\"pass-percentage\"></span> <div class=\"progress light-blue lighten-3\"> <div class=\"determinate light-blue\" style=\"width:67%\"></div> </div> </div> </div></div>";
		
		String jsPercentage = "var totalTC = $('#dashboard-view .col:nth-child(1) .panel-lead').text(); "+
				  "var totalPassed = $('#charts-row .text-small:eq(0) .strong').text(); "+
				  "var totalSkipped = $('#charts-row .text-small:eq(1) .strong:eq(1)').text(); "+
				  "var percentage = ((parseInt(totalPassed)+parseInt(totalSkipped))*100)/parseInt(totalTC); "+
				  "var passPercentage = parseFloat(parseFloat(percentage).toFixed(2));"+
				  "$('.card-panel .pass-percentage').text(passPercentage+'%');";
		
		String js = "$('#charts-row > div:eq(1)').hide(); "+
			   		"$('#charts-row').append('" + html + "');"+
					"$('#dashboard-view .card-panel .row div:nth-child(7)').hide(); "+
					"$('#dashboard-view .card-panel .row div:nth-child(4)').hide(); "+
					"$('#dashboard-view .card-panel .row div:nth-child(3)').hide(); "+
					"$('#dashboard-view .card-panel .row div:nth-child(2)').hide(); "+ jsPercentage +
					"$('#dashboard-view .card-panel .row div:nth-child(5) .panel-lead').text('"+ ps.getDuration() +"'); "+
					"$('.row:eq(1) .card-panel:eq(0), .row:eq(1) .card-panel:eq(4)').css('font-weight','bold'); "+
					"$('.row:eq(1) .card-panel:eq(0) .panel-lead, .row:eq(1) .card-panel:eq(4) .panel-lead').css('font-weight','normal'); "+
					"$('#controls div.controls div:eq(0)').hide(); $('#controls div.controls div:eq(1)').hide(); $('#toggle-test-view-charts').hide(); ";
		
		//To resolve responsive issues
				js += "$('.row:eq(1) > div:eq(0)').removeClass('s2').addClass('s12 m3 l3'); "+
					  "$('.row:eq(1) > div:eq(4)').removeClass('s2').addClass('s12 m3 l3'); "+
					  "$('.row:eq(1) > div:eq(5)').removeClass('s4').addClass('s12 m6 l6'); ";
				
				js += "$('#slide-out li:last a').click();";
        		
		String css = "#dashboard-view .r{ min-height: 191px; }  .card-panel .panel-lead{ top: 16px; left: 110px; } #parent-analysis{ width: 160px !important; height: 130px !important; margin-top: 30px;} "+
				 "#charts-row .card-panel{ height: 275px; } .progress{ margin: 6.5rem 0 1rem; } .card-panel .pass-percentage{ display: block; font-size: 20px; margin-top: 70px; text-align: center; }"+
				 ".row .col.s4{ width: 49.333%;} .row .col.s2{ width: 25.333%; }";
		
		//To resolve responsive issues
		css += " @media only screen and (max-width: 992px) { "+
				 ".side-nav.fixed { -webkit-transform: translateX(0); transform: translateX(0); display: block !important; } "+
				 "nav .brand-logo { left: 35px; } "+
				 ".extent div.container { padding-left: 70px; } }";
		
		//System.out.println("Device: " + ps.getOsname());
		
			ExtentHtmlReporter extentReporter = new ExtentHtmlReporter(AppConstant.ASSETS_PATH+"extentReport/"+ps.getExtentReportPath());
			extentReporter.config().setChartVisibilityOnOpen(false);
			extentReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
			extentReporter.config().setDocumentTitle("ExtentReports");
			extentReporter.config().setReportName("Automation -- Sprint-00");
			extentReporter.config().setJS(js);
			extentReporter.config().setCSS(css);
			
			ExtentReports extent = ExtentManager.getInstance();
			extent.attachReporter(extentReporter);
			extent.setSystemInfo("Device", ps.getOsname());
			extent.setSystemInfo("OS", ps.getBrowser());
			extent.flush();
	}
	
}
