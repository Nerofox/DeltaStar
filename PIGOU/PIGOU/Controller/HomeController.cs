using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace PIGOU.Controller
{
    class HomeController : BaseController
    {
        public HomeController(View.Home ihm) : base(ihm)
        {
            ihm.btnSettings.Click += BtnSettings_Click;
        }

        private void BtnSettings_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            this.ihm.NavigationService.Navigate(new View.Settings());
        }
    }
}
