using PIGOU.View;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace PIGOU.Controller
{
    class SettingsController : BaseController
    {
        public SettingsController(Settings ihm) : base(ihm)
        {
            ihm.btnBack.Click += BtnBack_Click;
        }

        private void BtnBack_Click(object sender, System.Windows.RoutedEventArgs e)
        {
            this.ihm.NavigationService.GoBack();
        }
    }
}
